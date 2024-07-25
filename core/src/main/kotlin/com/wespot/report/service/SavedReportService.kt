package com.wespot.report.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.message.port.out.MessagePort
import com.wespot.report.Report
import com.wespot.report.ReportType
import com.wespot.report.RestrictionService
import com.wespot.report.dto.ReportRequest
import com.wespot.report.dto.ReportResponse
import com.wespot.report.port.`in`.SavedReportUseCase
import com.wespot.report.port.out.ReportPort
import com.wespot.user.User
import com.wespot.user.port.out.UserPort
import com.wespot.vote.port.out.VotePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SavedReportService(
    private val reportPort: ReportPort,
    private val userPort: UserPort,
    private val votePort: VotePort,
    private val messagePort: MessagePort,
    private val restrictionService: RestrictionService
) : SavedReportUseCase {

    @Transactional
    override fun reportReceived(reportRequest: ReportRequest): ReportResponse {
        val loginUser = findLoginUser()
        val targetUser = findTargetUserByUserId(reportRequest.targetId)
        val report = Report.of(reportRequest.reportType, reportRequest.targetId, loginUser, targetUser)
        val reports = findAllUserReportByReportType(targetUser, reportRequest.reportType)
        executeReport(report, targetUser, reports)

        return ReportResponse(targetUser.id)
    }

    private fun executeReport(
        report: Report,
        targetUser: User,
        reports: List<Report>
    ) {
        validateReport(report)

        deleteIfMessageReport(report)
        val restriction = restrictionService.calculateRestrictionByReports(targetUser.restriction, report, reports)
        targetUser.restrict(restriction)

        reportPort.save(report)
        userPort.save(targetUser)
    }

    private fun findLoginUser(): User {
        return userPort.findById(SecurityUtils.getLoginUserId(userPort))
            ?: throw IllegalArgumentException("로그인을 하지 않은 유저입니다.")
    }

    private fun findTargetUserByUserId(userId: Long): User {
        return userPort.findById(userId) ?: throw IllegalArgumentException("신고 하고자 하는 회원이 존재하지 않습니다.")
    }


    private fun findAllUserReportByReportType(targetUser: User, reportType: ReportType): List<Report> {
        return reportPort.findAllByReportedIdAndReportType(targetUser.id, reportType)
    }

    private fun validateReport(report: Report) {
        if (existsTargetByReportType(report)) {
            return
        }

        throw IllegalArgumentException("존재하지 않는 ${report.reportType.value}에 신고를 할 수 없습니다.")
    }

    private fun existsTargetByReportType(report: Report): Boolean {
        if (report.isMessageReport()) {
            return messagePort.existsById(report.targetId)
        }

        return votePort.existsById(report.targetId)
    }

    private fun deleteIfMessageReport(report: Report) {
        if (report.isVoteReport()) {
            return
        }

        messagePort.deleteById(report.targetId)
    }

}

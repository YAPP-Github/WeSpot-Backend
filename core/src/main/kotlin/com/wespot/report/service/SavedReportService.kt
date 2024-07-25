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
        val targetUser = findTargetUserByUserId(reportRequest.targetUserId)
        val report = Report.of(reportRequest.reportType, reportRequest.targetId, loginUser, targetUser)
        val reports = findAllUserReportByReportType(targetUser, reportRequest.reportType)
        val savedReport = executeReport(report = report, sender = loginUser, receiver = targetUser, reports = reports)

        return ReportResponse(savedReport.id)
    }

    private fun findLoginUser(): User {
        return userPort.findById(SecurityUtils.getLoginUserId(userPort))!!
    }

    private fun findTargetUserByUserId(userId: Long): User {
        return userPort.findById(userId)!!
    }


    private fun findAllUserReportByReportType(targetUser: User, reportType: ReportType): List<Report> {
        return reportPort.findAllByReportedIdAndReportType(targetUser.id, reportType)
    }

    private fun executeReport(
        report: Report,
        sender: User,
        receiver: User,
        reports: List<Report>
    ): Report {
        validateReport(report, sender, receiver)

        deleteIfMessageReport(report)
        val restriction = restrictionService.calculateRestrictionByReports(receiver.restriction, report, reports)
        receiver.restrict(restriction)

        userPort.save(receiver)

        return reportPort.save(report)
    }

    private fun validateReport(report: Report, sender: User, receiver: User) {
        if (existsTargetByReportType(report, sender, receiver)) {
            return
        }

        throw IllegalArgumentException("존재하지 않는 ${report.reportType.value}에 신고를 할 수 없습니다.")
    }

    private fun existsTargetByReportType(report: Report, sender: User, receiver: User): Boolean {
        if (report.isMessageReport()) {
            return messagePort.existsByIdAndSenderIdAndReceiverId(report.targetId, sender.id, receiver.id)
        }

        return votePort.existsById(report.targetId) && sender.isClassmate(receiver)
    }

    private fun deleteIfMessageReport(report: Report) {
        if (report.isVoteReport()) {
            return
        }

        messagePort.deleteById(report.targetId)
    }

}

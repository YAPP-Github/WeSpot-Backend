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
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SavedReportService(
    private val reportPort: ReportPort,
    private val userPort: UserPort,
    private val messagePort: MessagePort,
    private val restrictionService: RestrictionService
) : SavedReportUseCase {

    @Transactional
    override fun reportReceived(reportRequest: ReportRequest): ReportResponse {
        val loginUser = findLoginUser()
        val targetUser = findTargetUserByReportRequest(loginUser, reportRequest.targetId, reportRequest.reportType)
        val report = Report.of(reportRequest.reportType, reportRequest.targetId, loginUser, targetUser)
        val reports = findAllUserReportByReportType(targetUser, reportRequest.reportType)
        val savedReport = executeReport(report = report, sender = loginUser, receiver = targetUser, reports = reports)

        return ReportResponse(savedReport.id)
    }

    private fun findLoginUser(): User {
        return userPort.findById(SecurityUtils.getLoginUserId(userPort))
            ?: throw NoSuchElementException("신고자가 로그인하지 않았습니다.")
    }

    private fun findTargetUserByReportRequest(loginUser: User, targetId: Long, reportType: ReportType): User {
        if (reportType == ReportType.VOTE) {
            return findTargetUserByUserId(targetId)
        }

        val message = messagePort.findById(targetId)
            ?: throw NoSuchElementException("신고하고자 하는 쪽지가 존재하지 않습니다.")
        validateReceiverId(loginUser.id, message.receiverId)

        return findTargetUserByUserId(message.senderId)
    }

    private fun validateReceiverId(loginUserId: Long, receiverId: Long) {
        require(loginUserId == receiverId) { "본인이 받은 쪽지가 아닙니다." }
    }

    private fun findTargetUserByUserId(userId: Long): User {
        return userPort.findById(userId)
            ?: throw NoSuchElementException("신고하고자 하는 사용자가 존재하지 않습니다.")
    }

    private fun findAllUserReportByReportType(targetUser: User, reportType: ReportType): List<Report> {
        return reportPort.findAllByReceiverIdAndReportType(targetUser.id, reportType)
    }

    private fun executeReport(
        report: Report,
        sender: User,
        receiver: User,
        reports: List<Report>
    ): Report {
        validateReport(sender, receiver)

        deleteIfMessageReport(report)
        val restriction = restrictionService.calculateRestrictionByReports(receiver.restriction, report, reports)
        receiver.restrict(restriction)

        userPort.save(receiver)

        return reportPort.save(report)
    }

    private fun validateReport(sender: User, receiver: User) {
        if (sender.isClassmate(receiver)) {
            return
        }

        throw IllegalArgumentException("같은 반 친구가 아닙니다.")
    }

    private fun deleteIfMessageReport(report: Report) {
        if (report.isVoteReport()) {
            return
        }

        val message = messagePort.findById(report.targetId) ?: throw IllegalArgumentException("존재하지 않는 쪽지입니다.")
        val reportedMessage = message.reported(report.senderId)
        messagePort.save(reportedMessage)
    }

}

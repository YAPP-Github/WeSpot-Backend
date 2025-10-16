package com.wespot.report.service

import com.wespot.exception.CustomException
import com.wespot.report.Report
import com.wespot.report.ReportType
import com.wespot.report.port.out.ReportPort
import com.wespot.user.port.out.RestrictionPort
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreatedReportService(
    val reportPort: ReportPort,
    val userPort: UserPort,
    val restrictionPort: RestrictionPort,
) {

    @Transactional
    fun saveReport(
        reportType: ReportType,
        targetId: Long,
        senderId: Long,
        receiverId: Long,
        content: String = "",
    ) {
        val report = Report(
            reportType = reportType,
            targetId = targetId,
            senderId = senderId,
            receiverId = receiverId,
            content = content
        )
        changeRestriction(report, receiverId, reportType)
    }

    fun changeRestriction(newReport: Report? = null, receiverId: Long, reportType: ReportType) {
        val reports = reportPort.findAllByReceiverIdAndReportType(receiverId, reportType)

        val targetUser = userPort.findById(receiverId) ?: throw CustomException(message = "존재하지 않는 유저입니다.")
        val restriction = targetUser.restriction
        val newRestriction = restriction.receivedNewReport(
            restrictionCategory = reportType.toRestrictionCategory(),
            reportCount = reports.size.toLong() + if (newReport != null) 1 else 0
        )
        targetUser.restrict(newRestriction)

        newReport?.let { reportPort.save(newReport) }
        restrictionPort.save(newRestriction)
        userPort.save(targetUser)
    }

}

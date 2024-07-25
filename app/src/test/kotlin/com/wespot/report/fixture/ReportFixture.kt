package com.wespot.report.fixture

import com.wespot.report.Report
import com.wespot.report.ReportType
import java.time.LocalDateTime

object ReportFixture {

    fun createWithReportType(reportType: ReportType) = Report(
        id = 0L,
        reportType = reportType,
        targetId = 1,
        senderId = 1,
        receiverId = 2,
        createdAt = LocalDateTime.now()
    )

    fun createWithReportTypeAndSenderIdAndReceiverId(reportType: ReportType, senderId: Long, receiverId: Long) = Report(
        id = 0L,
        reportType = reportType,
        targetId = 1,
        senderId = senderId,
        receiverId = receiverId,
        createdAt = LocalDateTime.now()
    )

    fun createWithReportTypeAndTargetIdAndSenderIdAndReceiverId(
        reportType: ReportType,
        targetId: Long,
        senderId: Long,
        receiverId: Long
    ) = Report(
        id = 0L,
        reportType = reportType,
        targetId = targetId,
        senderId = senderId,
        receiverId = receiverId,
        createdAt = LocalDateTime.now()
    )

}

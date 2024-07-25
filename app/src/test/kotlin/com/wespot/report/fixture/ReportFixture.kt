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
        receiverId = 1,
        createdAt = LocalDateTime.now()
    )

}

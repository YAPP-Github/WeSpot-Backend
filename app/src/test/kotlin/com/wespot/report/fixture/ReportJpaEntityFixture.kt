package com.wespot.report.fixture

import com.wespot.common.BaseEntity
import com.wespot.report.ReportJpaEntity
import com.wespot.report.ReportType
import java.time.LocalDateTime

object ReportJpaEntityFixture {

    fun createWithReportType(reportType: ReportType) = ReportJpaEntity(
        id = 0L,
        reportType = reportType,
        targetId = 1,
        senderId = 1,
        receiverId = 1,
        baseEntity = BaseEntity(LocalDateTime.now(), LocalDateTime.now())
    )
}

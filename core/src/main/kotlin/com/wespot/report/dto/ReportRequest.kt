package com.wespot.report.dto

import com.wespot.report.ReportType

data class ReportRequest(
    val targetId: Long,
    val targetUserId: Long,
    val reportType: ReportType
)

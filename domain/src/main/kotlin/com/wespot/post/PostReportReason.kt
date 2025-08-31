package com.wespot.post

import com.wespot.report.ReportReasonWithCustomReason
import java.time.LocalDateTime

data class PostReportReason(
    val id: Long = 0L,
    val postReportId: Long = 0L,
    val reportReasonWithCustomReason: ReportReasonWithCustomReason,

    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {

}

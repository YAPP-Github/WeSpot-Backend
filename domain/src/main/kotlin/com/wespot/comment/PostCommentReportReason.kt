package com.wespot.comment

import com.wespot.report.ReportReasonWithCustomReason
import java.time.LocalDateTime

data class PostCommentReportReason(
    val id: Long = 0L,
    val postCommentReportId: Long = 0L,
    val reportReasonWithCustomReason: ReportReasonWithCustomReason,

    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {

    fun registeredInPostCommentReport(postCommentReportId: Long): PostCommentReportReason {
        return copy(postCommentReportId = postCommentReportId)
    }

}

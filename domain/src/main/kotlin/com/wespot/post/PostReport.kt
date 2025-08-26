package com.wespot.post

import com.wespot.report.ReportReason
import java.time.LocalDateTime

class PostReport(
    val id: Long = 0L,
    val postId: Long,
    val userId: Long,
    val reportReason: ReportReason,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
}

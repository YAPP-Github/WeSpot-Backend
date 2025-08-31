package com.wespot.post

import java.time.LocalDateTime

class PostReport(
    val id: Long = 0L,
    val postId: Long,
    val userId: Long,
    val postReportReasons: List<PostReportReason> = mutableListOf(),
    val createdAt: LocalDateTime = LocalDateTime.now()
) {

    fun addReportReasons(postReportReasons: List<PostReportReason>) {
        postReportReasons.forEach {
            this.postReportReasons.plus(it)
        }
    }

}

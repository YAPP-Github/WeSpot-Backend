package com.wespot.comment

import java.time.LocalDateTime

class PostCommentReport(
    val id: Long = 0L,
    val postCommentId: Long,
    val userId: Long,
    val postCommentReportReasons: List<PostCommentReportReason> = mutableListOf(),
    val createdAt: LocalDateTime = LocalDateTime.now()
) {

    fun addReportReasons(postCommentReportReason: List<PostCommentReportReason>) {
        postCommentReportReason.forEach {
            this.postCommentReportReasons.plus(it)
        }
    }

}

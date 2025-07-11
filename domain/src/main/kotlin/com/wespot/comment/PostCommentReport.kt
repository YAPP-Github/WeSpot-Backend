package com.wespot.comment

import java.time.LocalDateTime

class PostCommentReport(
    val id: Long = 0L,
    val postCommentId: Long,
    val userId: Long,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
}

package com.wespot.comment

import java.time.LocalDateTime

class PostCommentLike(
    val id: Long,
    val postCommentId: Long,
    val userId: Long,
    val createdAt: LocalDateTime
) {
}

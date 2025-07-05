package com.wespot.comment

import java.time.LocalDateTime

class CommentLike(
    val id: Long,
    val commentId: Long,
    val userId: Long,
    val createdAt: LocalDateTime
) {
}

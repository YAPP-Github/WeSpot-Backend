package com.wespot.comment

import java.time.LocalDateTime

class CommentReport(
    val id: Long,
    val commentId: Long,
    val userId: Long,
    val createdAt: LocalDateTime
) {
}

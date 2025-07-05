package com.wespot.comment

import java.time.LocalDateTime

class PostComment(
    val id: Long,
    val postId: Long,
    val userId: Long,
    val content: String,
    val likeCount: Long,
    val reportCount: Long,
    val createdAt: LocalDateTime
) {
}

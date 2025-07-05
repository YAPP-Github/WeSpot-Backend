package com.wespot.post

import java.time.LocalDateTime

class PostLike(
    val id: Long,
    val postId: Long,
    val userId: Long,
    val createdAt: LocalDateTime
) {
}

package com.wespot.post

import java.time.LocalDateTime

class PostScrap(
    val id: Long,
    val postId: Long,
    val userId: Long,
    val createdAt: LocalDateTime
) {
}

package com.wespot.post

import java.time.LocalDateTime

class PostBlock(
    val id: Long = 0L,
    val postId: Long,
    val userId: Long,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
}

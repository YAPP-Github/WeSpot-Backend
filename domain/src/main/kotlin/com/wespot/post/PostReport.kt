package com.wespot.post

import java.time.LocalDateTime

class PostReport(
    val id: Long = 0L,
    val postId: Long,
    val userId: Long,
    val reason: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
}

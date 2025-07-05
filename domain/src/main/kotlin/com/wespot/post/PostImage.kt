package com.wespot.post

import java.time.LocalDateTime

class PostImage(
    val id: Long,
    val postId: Long,
    val url: String,
    val createdAt:LocalDateTime
) {
}

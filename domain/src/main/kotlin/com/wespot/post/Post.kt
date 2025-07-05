package com.wespot.post

import java.time.LocalDateTime

class Post(
    val id: Long,
    val category_id: Long,
    val title: String,
    val description: String,
    val likeCount: String,
    val commentCount: String,
    val createdAt: LocalDateTime,
) {
}

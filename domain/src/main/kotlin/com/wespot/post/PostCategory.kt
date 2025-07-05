package com.wespot.post

import java.time.LocalDateTime

class PostCategory(
    val id: Long,
    val majorCategoryName: String,
    val name: String,
    val createdAt: LocalDateTime
) {
}

package com.wespot.post

import java.time.LocalDateTime

data class PostProfile(
    val id: Long,
    val userId: Long,
    val url: Long,
    val name: String,
    val createdAt: LocalDateTime
) {
}

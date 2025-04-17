package com.wespot.user.message

import com.wespot.user.User
import java.time.LocalDateTime

class AnonymousProfile(
    val id: Long,
    val imageUrl: String,
    val name: String,
    val ownerId: User,
    val receiverId: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
}

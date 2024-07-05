package com.wespot.notification

import com.wespot.user.User
import java.time.LocalDateTime

data class Notification(
    val id: Long,
    val user: User,
    val type: NotificationType,
    val targetId: Long,
    val content: String,
    val isRead: Boolean,
    val readAt: LocalDateTime,
    val isEnabled: Boolean,
    val createdAt: LocalDateTime,
) {
}
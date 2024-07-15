package com.wespot.message

import com.wespot.user.User
import java.time.LocalDateTime

data class Message(
    val id: Long,
    val content: String,
    val sender: User,
    val receiver: User,
    val isReceiverRead: Boolean,
    val readAt: LocalDateTime,
    val isSent: Boolean,
    val sentAt: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val receivedAt: LocalDateTime,
) {
}

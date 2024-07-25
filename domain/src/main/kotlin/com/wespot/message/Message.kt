package com.wespot.message

import java.time.LocalDateTime

data class Message(
    val id: Long,
    val content: String,
    val senderId: Long,
    val receiverId: Long,
    val isReceiverRead: Boolean,
    val readAt: LocalDateTime,
    val isSent: Boolean,
    val sentAt: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val receivedAt: LocalDateTime,
) {
}

package com.wespot.message

import java.time.LocalDateTime

data class MessageV2(
    val id: Long,
    val content: MessageContent,
    val senderId: Long,
    val senderName: String,
    val receiverId: Long,
    val isReceiverRead: Boolean,
    val isAnonymous: Boolean,
    val messageType: MessageType,
    val readAt: LocalDateTime?,
    val isSend: Boolean,
    val sendAt: LocalDateTime?,
    val receivedAt: LocalDateTime?,
    val isReported: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val isSenderDeleted: Boolean,
    val senderDeletedAt: LocalDateTime?,
    val isReceiverDeleted: Boolean,
    val receiverDeletedAt: LocalDateTime?,

    val messageRoomId: Long?,
    val anonymousProfileId: Long?,
    val messageRoomOwnerId: Long?,
    val isBookmarked: Boolean?
) {
}

package com.wespot.message.dto.request

data class UpdateMessageRequest(
    val content: String,
    val receiverId: Long,
    val senderName: String
)

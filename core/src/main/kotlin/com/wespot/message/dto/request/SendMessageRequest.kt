package com.wespot.message.dto.request

data class SendMessageRequest(
    val content: String,
    val receiverId: Long,
    val senderName: String,
    val isAnonymous: Boolean
)

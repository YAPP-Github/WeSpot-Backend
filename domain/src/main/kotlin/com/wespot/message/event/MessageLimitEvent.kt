package com.wespot.message.event

data class MessageLimitEvent(
    val messageId: Long,
    val sendMessageCount: Int
)

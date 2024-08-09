package com.wespot.message.event

data class MessageLimitEvent(
    val senderId: Long,
    val sendMessageCount: Int
)

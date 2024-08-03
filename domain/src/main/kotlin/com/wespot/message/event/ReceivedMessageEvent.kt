package com.wespot.message.event

import com.wespot.user.User

data class ReceivedMessageEvent(
    val receiver: User,
    val messageId: Long
) {
}

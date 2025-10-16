package com.wespot.message.event

import com.wespot.message.v2.MessageV2
import com.wespot.user.User

data class MessageV2BlockedEvent(
    val sender: User,
    val receiver: User,
    val message: MessageV2
) {
}

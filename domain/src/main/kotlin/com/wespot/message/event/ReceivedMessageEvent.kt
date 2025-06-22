package com.wespot.message.event

import com.wespot.message.v2.MessageV2
import com.wespot.user.User
import com.wespot.user.message.AnonymousProfile

data class ReceivedMessageEvent(
    val sender: User,
    val senderAnonymousProfile: AnonymousProfile?,
    val receiver: User,
    val receiverAnonymousProfile: AnonymousProfile?,
    val message: MessageV2
) {
}

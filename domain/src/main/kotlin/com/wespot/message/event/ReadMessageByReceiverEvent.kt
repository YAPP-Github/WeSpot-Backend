package com.wespot.message.event

import com.wespot.user.User

data class ReadMessageByReceiverEvent(
    val sender: User,
    val messageId: Long
) {
}

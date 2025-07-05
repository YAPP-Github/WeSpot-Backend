package com.wespot.message.event

import com.wespot.user.User

class ReceivedMessageEventV1(
    val receiver: User,
    val messageId: Long,
) {
}

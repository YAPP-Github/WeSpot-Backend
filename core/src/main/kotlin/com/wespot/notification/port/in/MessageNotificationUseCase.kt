package com.wespot.notification.port.`in`

import com.wespot.user.User

interface MessageNotificationUseCase {

    fun openMessage()

    fun receiveMessage(receiver: User, messageId: Long)

    fun readMessageByReceiver(sender: User, receiver: User, messageId: Long, beforeIsReceiverRead: Boolean)

}

package com.wespot.notification.port.`in`

import com.wespot.message.v2.MessageV2
import com.wespot.user.User
import com.wespot.user.message.AnonymousProfile

interface MessageNotificationUseCase {

    fun openMessage()

    fun receivedMessageV1(
        receiver: User,
        messageId: Long,
    )

    fun receiveMessage(
        sender: User,
        senderAnonymousProfile: AnonymousProfile?,
        receiver: User,
        receiverAnonymousProfile: AnonymousProfile?,
        message: MessageV2,
    )

    fun readMessageByReceiver(
        sender: User,
        senderAnonymousProfile: AnonymousProfile?,
        receiver: User,
        receiverAnonymousProfile: AnonymousProfile?,
        messageId: Long,
        beforeIsReceiverRead: Boolean,
    )

    fun answerMessage(
        sender: User,
        senderAnonymousProfile: AnonymousProfile?,
        receiver: User,
        receiverAnonymousProfile: AnonymousProfile?,
        message: MessageV2,
    )

}

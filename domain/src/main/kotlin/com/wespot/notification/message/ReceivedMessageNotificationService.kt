package com.wespot.notification.message

import com.wespot.message.v2.MessageV2
import com.wespot.notification.Notification
import com.wespot.notification.NotificationType
import com.wespot.user.User
import com.wespot.user.message.AnonymousProfile
import org.springframework.stereotype.Component

@Component
class ReceivedMessageNotificationService {

    fun getNotificationV1(
        receiver: User,
        messageId: Long
    ): Notification {
        return Notification.createMessageInitialState(
            receiver.id,
            NotificationType.MESSAGE_RECEIVED,
            messageId,
            "누군가의 소중한 마음이 담긴 쪽지가 도착했어요 \uD83D\uDC8C",
            "${receiver.name}님에게 전하고 싶은 이야기가 있대요",
        )
    }


    fun getNotificationV2(
        sender: User,
        senderAnonymousProfile: AnonymousProfile?,
        receiver: User,
        receiverAnonymousProfile: AnonymousProfile?,
        message: MessageV2
    ): Notification {
        val receiverName = receiverAnonymousProfile?.name ?: receiver.name

        return Notification.createMessageInitialState(
            receiver.id,
            NotificationType.MESSAGE_RECEIVED,
            message.id,
            "누군가의 소중한 마음이 담긴 쪽지가 도착했어요 \uD83D\uDC8C",
            "${receiverName}님에게 전하고 싶은 이야기가 있대요",
        )
    }

}

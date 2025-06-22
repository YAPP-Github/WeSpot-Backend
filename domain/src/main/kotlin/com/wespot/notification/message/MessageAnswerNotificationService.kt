package com.wespot.notification.message

import com.wespot.message.v2.MessageV2
import com.wespot.notification.Notification
import com.wespot.notification.NotificationType
import com.wespot.user.User
import com.wespot.user.message.AnonymousProfile
import org.springframework.stereotype.Component

@Component
class MessageAnswerNotificationService {

    fun getNotification(
        sender: User,
        senderAnonymousProfile: AnonymousProfile?,
        receiver: User,
        receiverAnonymousProfile: AnonymousProfile?,
        message: MessageV2
    ): Notification {
        val senderName = senderAnonymousProfile?.name ?: sender.name
        val receiverName = receiverAnonymousProfile?.name ?: receiver.name

        return Notification.createMessageInitialState(
            receiver.id,
            NotificationType.ANSWER_MESSAGE,
            message.id,
            "방금 ${senderName}님이 내가 보낸 쪽지에 답장했어요 \uD83E\uDEE2",
            "앞으로도 ${receiverName}님이 쪽지를 받으실 때마다 알림을 드리도록 할게요!",
        )
    }

}

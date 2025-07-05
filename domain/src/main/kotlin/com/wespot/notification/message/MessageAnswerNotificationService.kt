package com.wespot.notification.message

import com.wespot.common.NotificationUtil
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

        return Notification.createMessageInitialState(
            receiver.id,
            NotificationType.ANSWER_MESSAGE,
            message.id,
            senderName,
            NotificationUtil.summaryContent(content = message.content.content),
        )
    }

}

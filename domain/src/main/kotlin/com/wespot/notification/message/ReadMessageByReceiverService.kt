package com.wespot.notification.message

import com.wespot.notification.Notification
import com.wespot.notification.NotificationType
import com.wespot.user.User
import org.springframework.stereotype.Component

@Component
class ReadMessageByReceiverService {

    fun getNotification(sender: User, messageId: Long): Notification {
        return Notification.createMessageInitialState(
            sender.id,
            NotificationType.MESSAGE_SENT,
            messageId,
            "방금 누군가 내가 보낸 쪽지를 읽었어요! 누군지 확인해 볼까요?",
        )
    }

}

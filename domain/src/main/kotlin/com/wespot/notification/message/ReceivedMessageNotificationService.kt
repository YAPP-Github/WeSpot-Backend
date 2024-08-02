package com.wespot.notification.message

import com.wespot.notification.Notification
import com.wespot.notification.NotificationType
import com.wespot.user.User
import org.springframework.stereotype.Component

@Component
class ReceivedMessageNotificationService {

    fun getNotification(receiver: User, messageId: Long): Notification {
        return Notification.createMessageInitialState(
            receiver.id,
            NotificationType.MESSAGE_RECEIVED,
            messageId,
            "누군가의 소중한 마음이 담긴 쪽지가 도착했어요! 열어서 확인해 볼까요?",
        )
    }

}

package com.wespot.notification.message

import com.wespot.notification.Notification
import com.wespot.notification.NotificationType
import org.springframework.stereotype.Component

@Component
class ReceivedMessageNotificationService {

    fun getNotification(receiverId: Long, receiverName: String, messageId: Long): Notification {
        return Notification.createMessageInitialState(
            receiverId,
            NotificationType.MESSAGE_RECEIVED,
            messageId,
            "누군가의 소중한 마음이 담긴 쪽지가 도착했어요 \uD83D\uDC8C",
            "${receiverName}님에게 전하고 싶은 이야기가 있대요",
        )
    }

}

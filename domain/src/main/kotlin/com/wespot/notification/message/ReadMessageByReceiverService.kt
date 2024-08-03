package com.wespot.notification.message

import com.wespot.notification.Notification
import com.wespot.notification.NotificationType
import org.springframework.stereotype.Component

@Component
class ReadMessageByReceiverService {

    fun getNotification(senderId: Long, receiverName: String, messageId: Long, isReceiverRead: Boolean): Notification? {
        if (isReceiverRead) {
            return null
        }

        return Notification.createMessageInitialState(
            senderId,
            NotificationType.MESSAGE_SENT,
            messageId,
            "방금 ${receiverName}님이 내가 보낸 쪽지를 읽었어요 \uD83E\uDEE2",
            "앞으로도 에버가 큐피드가 되어 드릴게요",
        )
    }

}

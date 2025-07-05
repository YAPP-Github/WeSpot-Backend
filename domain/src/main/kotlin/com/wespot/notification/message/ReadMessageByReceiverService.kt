package com.wespot.notification.message

import com.wespot.notification.Notification
import com.wespot.notification.NotificationType
import org.springframework.stereotype.Component

@Component
class ReadMessageByReceiverService {

    fun getNotification(senderId: Long, receiverName: String, messageId: Long, alreadyReceiverRead: Boolean): Notification? {
        if (alreadyReceiverRead) {
            return null
        }

        return Notification.createMessageInitialState(
            senderId,
            NotificationType.MESSAGE_SENT,
            messageId,
            "방금 ${receiverName}님이 내가 보낸 쪽지를 읽었어요 \uD83E\uDEE2",
            "어떤 답장이 올까요?",
        )
    }

}

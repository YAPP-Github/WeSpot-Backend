package com.wespot.notification.message

import com.wespot.message.v2.MessageV2
import com.wespot.notification.Notification
import com.wespot.notification.NotificationType
import com.wespot.user.User
import org.springframework.stereotype.Component

@Component
class MessageAnswerNotificationService {

    fun getNotification(sender: User, receiver: User, message: MessageV2): Notification {
        return Notification.createMessageInitialState(
            receiver.id,
            NotificationType.ANSWER_MESSAGE,
            message.id,
            "방금 ${sender.name}님이 내가 보낸 쪽지에 답장했어요 \uD83E\uDEE2",
            "앞으로도 ${receiver.name}님이 쪽지를 받으실 때마다 알림을 드리도록 할게요!",
        )
    }

}

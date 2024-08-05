package com.wespot.firebase

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.MulticastMessage
import com.wespot.notification.NotificationInfo
import com.wespot.notification.port.out.NotificationServicePort
import com.wespot.user.User
import org.springframework.stereotype.Component
import java.util.*

@Component
class FirebaseNotificationService : NotificationServicePort {

    override fun sendMulticastNotification(users: List<User>, notificationInfo: NotificationInfo) {
        val tokens = users.filter { isPossibleNotification(it) }
            .map { it.fcm!!.fcmToken }

        if (tokens.isEmpty()) {
            return
        }

        val multicastMessage = MulticastMessage.builder()
            .setNotification(notificationInfo.getNotification())
            .addAllTokens(tokens)
            .putAllData(notificationInfo.getData())
            .build()

        pushNotification { FirebaseMessaging.getInstance().sendMulticast(multicastMessage) }
    }

    private fun isPossibleNotification(it: User) = Objects.nonNull(it.fcm) && it.setting.isEnableVoteNotification

    private fun pushNotification(messageSend: () -> Unit) {
        try {
            messageSend()
        } catch (e: Exception) {
            throw IllegalArgumentException("투표 전송에 실패했습니다.")
        }
    }

    override fun sendNotification(user: User, notificationInfo: NotificationInfo) {
        if (!isPossibleNotification(user)) {
            return
        }

        val message = Message.builder()
            .setNotification(notificationInfo.getNotification())
            .setToken(user.fcm!!.fcmToken)
            .putAllData(notificationInfo.getData())
            .build()

        pushNotification { FirebaseMessaging.getInstance().send(message) }
    }

}

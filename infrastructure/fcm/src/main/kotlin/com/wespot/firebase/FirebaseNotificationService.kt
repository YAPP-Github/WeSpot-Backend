package com.wespot.firebase

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.MulticastMessage
import com.wespot.notification.NotificationInfo
import com.wespot.notification.port.out.NotificationServicePort
import com.wespot.user.User
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class FirebaseNotificationService : NotificationServicePort {

    private val logger = LoggerFactory.getLogger(FirebaseNotificationService::class.java)

    override fun sendMulticastNotification(users: List<User>, notificationInfo: NotificationInfo) {
        val tokens = users.filter { isValidFcmToken(it) }
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

    private fun isValidFcmToken(it: User) =
        it.fcm != null && it.fcm!!.fcmToken != null && it.fcm!!.fcmToken!!.isNotBlank()

    private fun pushNotification(messageSend: () -> Unit) {
        try {
            messageSend()
        } catch (e: Exception) {
            logger.error("투표 전송에 실패했습니다.")
        }
    }

    override fun sendNotification(user: User, notificationInfo: NotificationInfo) {
        if (isValidFcmToken(user)) {
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

package com.wespot.notification.message

import com.wespot.notification.Notification
import org.springframework.stereotype.Component

@Component
class DisabledMessageNotificationByLimitService {

    fun disableMessageNotification(
        sendMessageCount: Int,
        fetchNotifications: () -> List<Notification>
    ): List<Notification> {
        val notifications = fetchNotifications()

        if (sendMessageCount != 3) {
            notifications.forEach { it.enableMessageNotification() }
            return notifications
        }

        notifications.forEach { it.disableMessageNotification() }
        return notifications
    }

}

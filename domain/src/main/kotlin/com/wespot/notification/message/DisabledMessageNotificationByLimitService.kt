package com.wespot.notification.message

import com.wespot.notification.Notification
import org.springframework.stereotype.Component

@Component
class DisabledMessageNotificationByLimitService {

    fun disableMessageNotification(
        messageId: Long,
        sendMessageCount: Int,
        fetchNotifications: () -> List<Notification>
    ): List<Notification> {
        if (sendMessageCount != 3) {
            return emptyList()
        }
        val notifications = fetchNotifications.invoke()
        notifications.forEach { it.disableMessageNotification() }
        return notifications
    }

}

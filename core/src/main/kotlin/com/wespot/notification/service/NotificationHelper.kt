package com.wespot.notification.service

import com.wespot.notification.Notification
import com.wespot.notification.NotificationFilter
import com.wespot.notification.NotificationInfo
import com.wespot.notification.NotificationType
import com.wespot.notification.port.out.NotificationServicePort
import com.wespot.user.User
import org.springframework.stereotype.Component

@Component
class NotificationHelper(
    private val notificationServicePort: NotificationServicePort,
    private val notificationFilter: NotificationFilter
) {

    fun sendNotifications(users: List<User>, notifications: List<Notification>, notificationType: NotificationType) {
        if (notifications.isEmpty()) {
            return
        }

        val filteredNotifications = notificationFilter.filterNotifications(users, notifications, notificationType)
        val notificationUserIdGroup = filteredNotifications.map { it.userId }.toHashSet()
        val notificationUsers = users.filter { notificationUserIdGroup.contains(it.id) }
        val notificationInfo = NotificationInfo.createInitialState(filteredNotifications[0])
        notificationServicePort.sendMulticastNotification(notificationUsers, notificationInfo)
    }

    fun sendNotification(user: User?, notification: Notification) {
        user ?: return
        notificationFilter.filterNotification(user, notification, NotificationType.MESSAGE) ?: return

        notificationServicePort.sendNotification(
            user,
            NotificationInfo.createInitialState(notification)
        )
    }

}

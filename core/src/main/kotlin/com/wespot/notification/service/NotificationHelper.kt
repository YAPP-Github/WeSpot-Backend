package com.wespot.notification.service

import com.wespot.notification.Notification
import com.wespot.notification.NotificationFilterService
import com.wespot.notification.NotificationInfo
import com.wespot.notification.port.out.NotificationServicePort
import com.wespot.user.User
import org.springframework.stereotype.Component

@Component
class NotificationHelper(
    private val notificationServicePort: NotificationServicePort,
    private val notificationFilterService: NotificationFilterService
) {

    fun sendNotifications(users: List<User>, notifications: List<Notification>) {
        val filteredNotifications = notificationFilterService.filterNotifications(users, notifications)

        if (filteredNotifications.isEmpty()) {
            return
        }

        val notificationUserIdGroup = filteredNotifications.map { it.userId }.toHashSet()
        val notificationUsers = users.filter { notificationUserIdGroup.contains(it.id) }
        val notificationInfo = NotificationInfo.createInitialState(filteredNotifications[0])
        notificationServicePort.sendMulticastNotification(notificationUsers, notificationInfo)
    }

    fun sendNotification(receiver: User?, notification: Notification) {
        receiver ?: return
        notificationFilterService.filterNotification(receiver, notification) ?: return

        notificationServicePort.sendNotification(
            receiver,
            NotificationInfo.createInitialState(notification)
        )
    }

}

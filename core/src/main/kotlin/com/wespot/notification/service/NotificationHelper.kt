package com.wespot.notification.service

import com.wespot.notification.Notification
import com.wespot.notification.NotificationInfo
import com.wespot.user.User
import org.springframework.stereotype.Component

@Component
class NotificationHelper(
    private val notificationSendService: NotificationSendService,
) {

    fun sendNotifications(users: List<User>, notifications: List<Notification>) {
        if (notifications.isEmpty()) {
            return
        }

        val notificationUserIdGroup = notifications.map { it.userId }.toHashSet()
        val notificationUsers = users.filter { notificationUserIdGroup.contains(it.id) }
        val notificationInfo = NotificationInfo.createInitialState(notifications[0])
        notificationSendService.sendMulticastNotification(notificationUsers, notificationInfo)
    }

    fun sendNotification(user: User?, notification: Notification) {
        user ?: return
        notificationSendService.sendNotification(
            user,
            NotificationInfo.createInitialState(notification)
        )
    }

}

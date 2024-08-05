package com.wespot.notification

import com.wespot.user.User
import org.springframework.stereotype.Component

@Component
class NotificationFilter {

    fun filterNotifications(
        users: List<User>,
        notifications: List<Notification>,
        notificationType: NotificationType
    ): List<Notification> {
        val usersGroup = users.associateBy { it.id }

        return notifications.filter { usersGroup.containsKey(it.userId) }
            .filter { getNotificationSettingBy(usersGroup[it.userId]!!, notificationType) }
    }

    private fun getNotificationSettingBy(
        user: User,
        notificationType: NotificationType
    ): Boolean {
        if (notificationType.isVote()) {
            return user.isEnableVoteNotification()
        }

        return user.isEnableMessageNotification()
    }

    fun filterNotification(
        user: User,
        notification: Notification,
        notificationType: NotificationType
    ): Notification? {
        if (getNotificationSettingBy(user, notificationType)) {
            return notification
        }

        return null
    }

}

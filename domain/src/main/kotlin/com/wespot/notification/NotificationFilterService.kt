package com.wespot.notification

import com.google.common.io.ByteArrayDataInput
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.user.User
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class NotificationFilterService {

    fun filterNotifications(
        users: List<User>,
        notifications: List<Notification>,
    ): List<Notification> {
        if (notifications.isEmpty()) {
            return notifications
        }

        val usersGroup = users.associateBy { it.id }
        validateNotificationType(notifications)

        return notifications.filter { usersGroup.containsKey(it.userId) }
            .filter { getNotificationSettingBy(usersGroup[it.userId]!!, notifications[0].type) }
    }

    private fun validateNotificationType(notifications: List<Notification>) {
        val notificationTypesCount = notifications.map { it.type }.toSet()
        require(notificationTypesCount.size == 1) {
            throw CustomException(
                HttpStatus.BAD_REQUEST,
                ExceptionView.TOAST,
                "한번에 동일한 NotificationType만을 발송할 수 있습니다."
            )
        }
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
    ): Notification? {
        if (getNotificationSettingBy(user, notification.type)) {
            return notification
        }

        return null
    }

}

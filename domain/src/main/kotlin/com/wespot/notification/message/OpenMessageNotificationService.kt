package com.wespot.notification.message

import com.wespot.notification.Notification
import com.wespot.notification.NotificationType
import com.wespot.user.User
import com.wespot.user.UserClass
import org.springframework.stereotype.Component

@Component
class OpenMessageNotificationService {

    fun getNotifications(users: List<User>): List<Notification> {
        val usersGroup: Map<UserClass, List<User>> = users.groupBy { UserClass.of(it) }
        return usersGroup.mapValues { createNotification(it) }
            .flatMap { it.value }
    }

    private fun createNotification(users: Map.Entry<UserClass, List<User>>): List<Notification> {
        if (users.value.size == 1) {
            return emptyList()
        }

        return users.value.map {
            Notification.createMessageInitialState(
                it.id,
                NotificationType.MESSAGE,
                0,
                "우리 반 쪽지함이 열렸어요! 익명 쪽지로 마음을 표현해 볼까요?",
            )
        }
    }

}

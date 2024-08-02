package com.wespot.notification.message

import com.wespot.notification.Notification
import com.wespot.notification.NotificationType
import com.wespot.user.User
import com.wespot.user.UserClass
import org.springframework.stereotype.Component

@Component
class OpenMessageNotificationService {

    fun getNotifications(users: List<User>): List<Notification> {
        if (users.size == 1) {
            return emptyList()
        }

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
                "${it.name}님의 마음을 들려주세요 에버가 전달해 드릴게요 \uD83D\uDC98",
                "오늘 ${it.name}님을 설레게 한 친구는 누구인가요?",
            )
        }
    }

}

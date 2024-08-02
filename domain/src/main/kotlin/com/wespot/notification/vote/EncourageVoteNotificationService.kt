package com.wespot.notification.vote

import com.wespot.notification.Notification
import com.wespot.notification.NotificationType
import com.wespot.user.User
import com.wespot.user.UserClass
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class EncourageVoteNotificationService {

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
            Notification.createVoteInitialState(
                it.id,
                NotificationType.VOTE,
                LocalDate.now(),
                "우리 반 투표가 진행 중이에요! 지금 바로 투표에 참여해 보세요",
            )
        }
    }

}

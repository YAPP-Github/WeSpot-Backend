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
        val now = LocalDate.now()
        return users.value.map {
            Notification.createVoteInitialState(
                it.id,
                NotificationType.VOTE,
                now,
                "${now.monthValue}월 ${now.dayOfMonth}일 우리 반 투표가 진행 중이에요 \uD83D\uDD25",
                "친구들이 ${it.name}님의 투표를 기다리고 있어요",
            )
        }
    }

}

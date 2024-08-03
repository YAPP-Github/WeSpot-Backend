package com.wespot.notification.vote

import com.wespot.notification.Notification
import com.wespot.notification.NotificationType
import com.wespot.user.User
import com.wespot.user.UserClass
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class EndVoteNotificationService {

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
                NotificationType.VOTE_RESULT,
                LocalDate.now().minusDays(1),
                "어제의 투표 결과를 분석했어요 \uD83D\uDCDD",
                "친구들은 어제 ${it.name}님을 어떻게 생각했을까요?",
            )
        }
    }

}

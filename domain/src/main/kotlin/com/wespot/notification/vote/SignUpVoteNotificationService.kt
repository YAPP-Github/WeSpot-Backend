package com.wespot.notification.vote

import com.wespot.notification.Notification
import com.wespot.notification.NotificationType
import com.wespot.user.User
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class SignUpVoteNotificationService {

    fun getNotifications(user: User, users: List<User>): List<Notification> {
        if (users.size % 2 != 0) {
            return emptyList()
        }

        return users.filter { it.id != user.id }
            .map {
                Notification.createVoteInitialState(
                    it.id,
                    NotificationType.VOTE,
                    LocalDate.now(),
                    "투표를 기다리고 있는 친구가 있어요! 지금 바로 투표해볼까요?"
                )
            }
    }

}

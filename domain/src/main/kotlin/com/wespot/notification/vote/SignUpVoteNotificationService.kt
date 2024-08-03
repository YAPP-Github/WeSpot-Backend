package com.wespot.notification.vote

import com.wespot.notification.ClassmateValidator
import com.wespot.notification.Notification
import com.wespot.notification.NotificationType
import com.wespot.user.User
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class SignUpVoteNotificationService {

    fun getNotifications(signUpUser: User, users: List<User>): List<Notification> {
        ClassmateValidator.validateContainNonClassmateUser(users)
        return users.filter { it.id != signUpUser.id }
            .map {
                Notification.createVoteInitialState(
                    it.id,
                    NotificationType.VOTE,
                    LocalDate.now(),
                    "${signUpUser.name}님이 위스팟에 입장했어요 \uD83D\uDE4B\uD83C\uDFFB",
                    "${it.name}님이 자신을 어떻게 생각하고 있을지 궁금하대요"
                )
            }
    }

}

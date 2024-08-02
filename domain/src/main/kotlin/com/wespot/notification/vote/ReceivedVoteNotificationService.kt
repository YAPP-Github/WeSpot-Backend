package com.wespot.notification.vote

import com.wespot.notification.Notification
import com.wespot.notification.NotificationType
import com.wespot.user.User
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class ReceivedVoteNotificationService {

    fun getNotification(user: User): Notification {
        val genderKeyword = getGenderKeyword(user.gender)

        return Notification.createVoteInitialState(
            userId = user.id,
            type = NotificationType.VOTE_RECEIVED,
            date = LocalDate.now(),
            content = "우리 반 ${genderKeyword}이 표를 보냈어요 어떤 내용인지 확인해볼까요?",
        )
    }

    private fun getGenderKeyword(gender: String): String {
        if (gender == "female") {
            return "여학생"
        }

        return "남학생"
    }

}

package com.wespot.notification.vote

import com.wespot.notification.Notification
import com.wespot.notification.NotificationType
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class ReceivedVoteNotificationService {

    fun getNotification(userId: Long, gender: String): Notification {
        val genderKeyword = getGenderKeyword(gender)

        return Notification.createVoteInitialState(
            userId = userId,
            type = NotificationType.VOTE_RECEIVED,
            date = LocalDate.now(),
            title = "방금 우리 반 ${genderKeyword}이 나에게 투표했어요 \uD83D\uDCA5",
            body = "어떤 내용일지 눌러서 바로 확인해 보세요",
        )
    }

    private fun getGenderKeyword(gender: String): String {
        if (gender == "female") {
            return "여학생"
        }

        return "남학생"
    }

}

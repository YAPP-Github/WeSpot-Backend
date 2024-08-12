package com.wespot.notification.domain.vote

import com.wespot.notification.NotificationType
import com.wespot.notification.vote.ReceivedVoteNotificationService
import com.wespot.user.Gender
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class ReceivedVoteNotificationServiceTest : BehaviorSpec({

    given("남학생이 투표를 완료했을 때") {
        val service = ReceivedVoteNotificationService()
        `when`("투표를 받은이에게") {
            val notification = service.getNotification(100L, Gender.MALE)
            then("알림이 발송된다.") {
                notification.userId shouldBe 100L
                notification.type shouldBe NotificationType.VOTE_RECEIVED
                notification.targetId shouldBe 0
                notification.date shouldBe LocalDate.now()
                notification.title shouldBe "방금 우리 반 여학생이 나에게 투표했어요 \uD83D\uDCA5"
                notification.body shouldBe "어떤 내용일지 눌러서 바로 확인해 보세요"
            }
        }
    }

    given("여학생이 투표를 완료했을 때") {
        val service = ReceivedVoteNotificationService()
        `when`("투표를 받은이에게") {
            val notification = service.getNotification(100L, Gender.MALE)
            then("알림이 발송된다.") {
                notification.userId shouldBe 100L
                notification.type shouldBe NotificationType.VOTE_RECEIVED
                notification.targetId shouldBe 0
                notification.date shouldBe LocalDate.now()
                notification.title shouldBe "방금 우리 반 남학생이 나에게 투표했어요 \uD83D\uDCA5"
                notification.body shouldBe "어떤 내용일지 눌러서 바로 확인해 보세요"
            }
        }
    }

})

package com.wespot.notification.domain.message

import com.wespot.notification.NotificationType
import com.wespot.notification.message.OpenMessageNotificationService
import com.wespot.user.fixture.UserFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class OpenMessageNotificationServiceTest : BehaviorSpec({

    given("메시지함이 열렸을 때") {
        val users = listOf(
            UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(1, 1, 1, 1),
            UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(2, 1, 1, 1),
            UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(3, 1, 1, 1),
            UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(4, 1, 1, 2),
            UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(5, 1, 1, 2),
            UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(6, 1, 1, 3)
        )
        val service = OpenMessageNotificationService()
        `when`("학급에 1명만 존재하는 인원을 빼고") {
            val notifications = service.getNotifications(users)
            val userSet = notifications.map { it.userId }.toSet()
            val typeSet = notifications.map { it.type }.toSet()
            val contentSet = notifications.map { it.title }.toSet()
            val targetIdSet = notifications.map { it.targetId }.toSet()
            val doesNotExistsSixthUser = notifications.stream().allMatch { it.userId != 6L }
            then("알림을 발생시킨다.") {
                notifications.size shouldBe 5
                userSet.size shouldBe 5
                typeSet.size shouldBe 1
                contentSet.size shouldBe 1
                targetIdSet.size shouldBe 1
                doesNotExistsSixthUser shouldBe true
                notifications[0].type shouldBe NotificationType.MESSAGE
                notifications[0].date shouldBe LocalDate.now()
                notifications[0].targetId shouldBe 0
                notifications[0].title shouldBe "TestUser님의 마음을 들려주세요 에버가 전달해 드릴게요 \uD83D\uDC98"
            }
        }
    }

})

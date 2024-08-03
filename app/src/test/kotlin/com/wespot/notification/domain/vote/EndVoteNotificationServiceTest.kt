package com.wespot.notification.domain.vote

import com.wespot.notification.NotificationType
import com.wespot.notification.vote.EndVoteNotificationService
import com.wespot.user.fixture.UserFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class EndVoteNotificationServiceTest : BehaviorSpec({

    given("오늘의 투표가 종료되었을 때") {
        val users = listOf(
            UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(1, 1, 1, 1),
            UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(2, 1, 1, 1),
            UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(3, 1, 1, 1),
            UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(4, 1, 1, 2),
            UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(5, 1, 1, 2),
            UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(6, 1, 1, 3)
        )
        val service = EndVoteNotificationService()
        `when`("학급에 인원이 1명만 존재하는 경우를 제외하고") {
            val notifications = service.getNotifications(users)
            val userSet = notifications.map { it.userId }.toSet()
            val typeSet = notifications.map { it.type }.toSet()
            val contentSet = notifications.map { it.title }.toSet()
            val targetIdSet = notifications.map { it.date }.toSet()
            val doesNotExistsSixthUser = notifications.stream().allMatch { it.userId != 6L }
            then("알림을 발생시킨다.") {
                notifications.size shouldBe 5
                userSet.size shouldBe 5
                typeSet.size shouldBe 1
                contentSet.size shouldBe 1
                targetIdSet.size shouldBe 1
                doesNotExistsSixthUser shouldBe true
                notifications[0].type shouldBe NotificationType.VOTE_RESULT
                notifications[0].date shouldBe LocalDate.now().minusDays(1)
                notifications[0].targetId shouldBe 0
                notifications[0].title shouldBe "어제의 투표 결과를 분석했어요 📝"
                notifications[0].body shouldBe "친구들은 어제 ${users[0].name}님을 어떻게 생각했을까요?"
            }
        }
    }

})

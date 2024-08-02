package com.wespot.notification.domain.vote

import com.wespot.notification.NotificationType
import com.wespot.notification.vote.EncourageVoteNotificationService
import com.wespot.user.fixture.UserFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class EncourageVoteNotificationServiceTest : BehaviorSpec({

    given("유저들에게 투표 독려 알림을") {
        val users = listOf(
            UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(1, 1, 1, 1),
            UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(2, 1, 1, 1),
            UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(3, 1, 1, 1),
            UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(4, 1, 1, 2),
            UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(5, 1, 1, 2),
            UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(6, 1, 1, 3)
        )
        val service = EncourageVoteNotificationService()
        `when`("학급에 1명만 존재하는 인원을 빼고") {
            val notifications = service.getNotifications(users)
            val userSet = notifications.map { it.userId }.toSet()
            val typeSet = notifications.map { it.type }.toSet()
            val contentSet = notifications.map { it.title }.toSet()
            val targetIdSet = notifications.map { it.date }.toSet()
            val doesNotExistsSixthUser = notifications.stream().allMatch { it.userId != 6L }
            then("알림을 발송한다.") {
                notifications.size shouldBe 5
                userSet.size shouldBe 5
                typeSet.size shouldBe 1
                contentSet.size shouldBe 1
                targetIdSet.size shouldBe 1
                doesNotExistsSixthUser shouldBe true
                notifications[0].type shouldBe NotificationType.VOTE
                notifications[0].date shouldBe LocalDate.now()
                notifications[0].targetId shouldBe 0
                notifications[0].title shouldBe "${LocalDate.now().monthValue}월 ${LocalDate.now().dayOfMonth}일 우리 반 투표가 진행 중이에요 \uD83D\uDD25"
            }
        }
    }

})

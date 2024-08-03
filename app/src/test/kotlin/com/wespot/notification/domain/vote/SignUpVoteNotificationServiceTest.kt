package com.wespot.notification.domain.vote

import com.wespot.notification.NotificationType
import com.wespot.notification.vote.SignUpVoteNotificationService
import com.wespot.user.fixture.UserFixture
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class SignUpVoteNotificationServiceTest : BehaviorSpec({

    given("반에 새로운 이가 가입했을 때") {
        val service = SignUpVoteNotificationService()
        `when`("반에 새로운 인원이 가입할 때") {
            val users = mutableListOf(
                UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(1, 1, 1, 1),
                UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(2, 1, 1, 1),
            )
            val notifications = service.getNotifications(users[0], users)

            then("알림이 발생한다.") {
                notifications.size shouldBe 1
                val doesNotExistsFirstUser = notifications.stream().allMatch { it.userId != users[0].id }
                doesNotExistsFirstUser shouldBe true
                notifications[0].type shouldBe NotificationType.VOTE
                notifications[0].date shouldBe LocalDate.now()
                notifications[0].targetId shouldBe 0
                notifications[0].title shouldBe "${users[0].name}님이 위스팟에 입장했어요 \uD83D\uDE4B\uD83C\uDFFB"
                notifications[0].body shouldBe "${users[1].name}님이 자신을 어떻게 생각하고 있을지 궁금하대요"
            }
        }
    }

    given("새로운 친구 알림 발생 메서드에") {
        val service = SignUpVoteNotificationService()
        `when`("서로 다른 학급의 친구가 들어온다면") {
            val users = mutableListOf(
                UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(1, 1, 1, 1),
                UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(2, 1, 1, 2),
            )
            val shouldThrow = shouldThrow<IllegalArgumentException> { service.getNotifications(users[0], users) }
            then("예외가 발생한다.") {
                shouldThrow.message shouldBe "다른 학급의 사용자가 포함되어 있습니다."
            }
        }
    }


})

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
        `when`("반에 존재하는 인원이 2의 배수가 아니라면") {
            val users = mutableListOf(
                UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(1, 1, 1, 1),
            )
            val notifications1 = service.getNotifications(users[0], users)
            then("알림이 발생하지 않는다.") {
                notifications1.size shouldBe 0
            }
            users.add(UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(2, 1, 1, 1))
            users.add(UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(3, 1, 1, 1))
            val notifications2 = service.getNotifications(users[0], users)
            then("알림이 발생하지 않는다.") {
                notifications2.size shouldBe 0
            }
        }
        `when`("반에 존재하는 인원이 2의 배수라면") {
            val users = mutableListOf(
                UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(1, 1, 1, 1),
                UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(2, 1, 1, 1),
            )
            val notifications1 = service.getNotifications(users[0], users)

            then("알림이 발생한다.") {
                notifications1.size shouldBe 1
                val userSet = notifications1.map { it.userId }.toSet()
                val typeSet = notifications1.map { it.type }.toSet()
                val contentSet = notifications1.map { it.title }.toSet()
                val targetIdSet = notifications1.map { it.date }.toSet()
                val doesNotExistsFirstUser = notifications1.stream().allMatch { it.userId != users[0].id }
                userSet.size shouldBe 1
                typeSet.size shouldBe 1
                contentSet.size shouldBe 1
                targetIdSet.size shouldBe 1
                doesNotExistsFirstUser shouldBe true
                notifications1[0].type shouldBe NotificationType.VOTE
                notifications1[0].date shouldBe LocalDate.now()
                notifications1[0].targetId shouldBe 0
                notifications1[0].title shouldBe "새로운 친구들이 위스팟에 입장했어요 \uD83D\uDE4B\uD83C\uDFFB"
            }
            users.add(UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(3, 1, 1, 1))
            users.add(UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(4, 1, 1, 1))
            val notifications2 = service.getNotifications(users[0], users)

            then("알림이 발생한다.") {
                val userSet = notifications2.map { it.userId }.toSet()
                val typeSet = notifications2.map { it.type }.toSet()
                val contentSet = notifications2.map { it.title }.toSet()
                val targetIdSet = notifications2.map { it.date }.toSet()
                val doesNotExistsFirstUser = notifications2.stream().allMatch { it.userId != users[0].id }
                notifications2.size shouldBe 3
                userSet.size shouldBe 3
                typeSet.size shouldBe 1
                contentSet.size shouldBe 1
                targetIdSet.size shouldBe 1
                doesNotExistsFirstUser shouldBe true
                notifications2[0].type shouldBe NotificationType.VOTE
                notifications2[0].date shouldBe LocalDate.now()
                notifications2[0].targetId shouldBe 0
                notifications2[0].title shouldBe "새로운 친구들이 위스팟에 입장했어요 \uD83D\uDE4B\uD83C\uDFFB"
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

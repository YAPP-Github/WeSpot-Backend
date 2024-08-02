package com.wespot.notification.domain.vote

import com.wespot.notification.NotificationType
import com.wespot.notification.vote.RegisteredVoteNotificationService
import com.wespot.user.fixture.UserFixture
import com.wespot.vote.Vote
import com.wespot.vote.VoteIdentifier
import com.wespot.voteoption.fixture.VoteOptionFixture
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import java.time.LocalDate
import java.time.LocalDateTime

class RegisteredVoteNotificationServiceTest : BehaviorSpec({

    given("투표에 참여한 인원이") {
        val service = RegisteredVoteNotificationService()

        `when`("5명 미만이거나 5명이 아니고 (5 + 3의 배수)가 아닌 경우") {
            val voteOptions = (1..5).map { VoteOptionFixture.createWithId(it.toLong()) }
            val users = (0..10).map { UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(it.toLong(), 1, 1, 1) }
            val voteIdentifier = VoteIdentifier.of(users[1], LocalDate.now())
            val vote = Vote.of(voteIdentifier, voteOptions, null)
            vote.addBallot(1, users[1], users[2], LocalDate.now().atStartOfDay())
            vote.addBallot(1, users[2], users[3], LocalDate.now().atStartOfDay())
            vote.addBallot(1, users[3], users[4], LocalDate.now().atStartOfDay())
            vote.addBallot(1, users[4], users[1], LocalDate.now().atStartOfDay())
            vote.addBallot(1, users[4], users[5], LocalDate.now().atStartOfDay())
            val notifications1 = service.getNotifications(users[1], users, vote)

            then("알림이 발생하지 않는다.") {
                notifications1.size shouldBe 0
            }

            vote.addBallot(1, users[5], users[7], LocalDateTime.now())
            vote.addBallot(1, users[6], users[8], LocalDateTime.now())
            vote.addBallot(1, users[6], users[9], LocalDateTime.now())
            val notifications2 = service.getNotifications(users[1], users, vote)

            then("알림이 발생하지 않는다.") {
                notifications2.size shouldBe 0
            }

            vote.addBallot(1, users[7], users[4], LocalDateTime.now())
            vote.addBallot(1, users[8], users[3], LocalDateTime.now())
            vote.addBallot(1, users[9], users[2], LocalDateTime.now())
            val notifications3 = service.getNotifications(users[1], users, vote)
            then("알림이 발생하지 않는다.") {
                notifications3.size shouldBe 0
            }
        }
        `when`("5명이거나, (5 + 3의 배수)가 아닌 경우") {
            val voteOptions = (1..5).map { VoteOptionFixture.createWithId(it.toLong()) }
            val users = (0..10).map { UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(it.toLong(), 1, 1, 1) }
            val voteIdentifier = VoteIdentifier.of(users[1], LocalDate.now())
            val vote = Vote.of(voteIdentifier, voteOptions, null)
            vote.addBallot(1, users[1], users[2], LocalDateTime.now())
            vote.addBallot(1, users[2], users[3], LocalDateTime.now())
            vote.addBallot(1, users[3], users[4], LocalDateTime.now())
            vote.addBallot(1, users[4], users[1], LocalDateTime.now())
            vote.addBallot(1, users[4], users[5], LocalDateTime.now())
            vote.addBallot(1, users[5], users[6], LocalDateTime.now())
            val notifications1 = service.getNotifications(users[1], users, vote)

            then("알림이 발생한다.") {
                val userSet = notifications1.map { it.userId }.toSet()
                val typeSet = notifications1.map { it.type }.toSet()
                val contentSet = notifications1.map { it.title }.toSet()
                val targetIdSet = notifications1.map { it.date }.toSet()
                val doesNotExistsFirstUser = notifications1.stream().allMatch { it.userId != users[1].id }
                notifications1.size shouldBe 10
                userSet.size shouldBe 10
                typeSet.size shouldBe 1
                contentSet.size shouldBe 1
                targetIdSet.size shouldBe 1
                doesNotExistsFirstUser shouldBe true
                notifications1[0].userId shouldBe 0
                notifications1[0].type shouldBe NotificationType.VOTE_RESULT
                notifications1[0].title shouldBe "우리 반 투표 결과가 업데이트 되었어요 \uD83D\uDC40"
                notifications1[0].date shouldBe LocalDate.now()
                notifications1[0].targetId shouldBe 0
            }

            vote.addBallot(1, users[6], users[1], LocalDateTime.now())
            vote.addBallot(1, users[7], users[2], LocalDateTime.now())
            vote.addBallot(1, users[8], users[3], LocalDateTime.now())
            vote.addBallot(1, users[8], users[4], LocalDateTime.now())
            then("알림이 발생한다.") {
                val userSet = notifications1.map { it.userId }.toSet()
                val typeSet = notifications1.map { it.type }.toSet()
                val contentSet = notifications1.map { it.title }.toSet()
                val targetIdSet = notifications1.map { it.date }.toSet()
                val doesNotExistsFirstUser = notifications1.stream().allMatch { it.userId != users[1].id }
                notifications1.size shouldBe 10
                userSet.size shouldBe 10
                typeSet.size shouldBe 1
                contentSet.size shouldBe 1
                targetIdSet.size shouldBe 1
                doesNotExistsFirstUser shouldBe true
            }
        }
    }

    given("투표에 참여한 인원 중") {
        val service = RegisteredVoteNotificationService()
        `when`("같은 반이 아닌 친구가 존재하면") {
            val voteOptions = (1..5).map { VoteOptionFixture.createWithId(it.toLong()) }
            val users = listOf(
                UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(1, 1, 1, 1),
                UserFixture.createWithIdAndSchoolIdAndGradeAndClassNumber(2, 1, 1, 2),
            )
            val vote = Vote.of(VoteIdentifier.of(users[0], LocalDate.now()), voteOptions, null)

            then("예외가 발생한다.") {
                val shouldThrow =
                    shouldThrow<IllegalArgumentException> { service.getNotifications(users[0], users, vote) }
                shouldThrow shouldHaveMessage "다른 학급의 사용자가 포함되어 있습니다."
            }
        }
    }

})

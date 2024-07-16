package com.wespot.vote.domain

import com.wespot.vote.Ballots
import com.wespot.vote.fixture.BallotFixture
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage

class BallotsTest : BehaviorSpec({

    given("투표지를 여러개 묶어") {
        val ballots = listOf(
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1, 1, 1, 2),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1, 1, 1, 3),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1, 1, 1, 4)
        )
        `when`("투표지 목록을") {
            val actual = Ballots.from(ballots)
            then("정상적으로 생성한다.") {
                actual.ballots.size shouldBe 1
                actual.ballots[1]!!.size shouldBe 3
            }
        }

        `when`("생성할 때, 동일한 사람이 똑같은 사람을 중복 투표하면") {
            val duplicateBallot = BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(
                1,
                1,
                1,
                4
            )
            val duplicateBallots = ballots.toMutableList()
            duplicateBallots.add(duplicateBallot)
            val throwingCallable = { Ballots.from(duplicateBallots) }
            then("예외가 발생한다.") {
                val shouldThrow = shouldThrow<IllegalArgumentException>(throwingCallable)
                shouldThrow shouldHaveMessage "하루에 한 명의 회원에게 한 개의 투표만 할 수 있습니다."
            }
        }

        `when`("이미 생성된 투표지 목록에, 중복된 투표지를 추가하면") {
            val actual = Ballots.from(ballots)
            val duplicateBallot = BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(
                1,
                1,
                1,
                4
            )
            val throwingCallable={actual.add(duplicateBallot)}
            then("예외가 발생한다.") {
                val shouldThrow = shouldThrow<IllegalArgumentException>(throwingCallable)
                shouldThrow shouldHaveMessage "하루에 한 명의 회원에게 한 개의 투표만 할 수 있습니다."
            }
        }

        `when`("투표지 목록을 생성한 뒤, 본인이 투표한 인원들의 ID를") {
            val actual = Ballots.from(ballots)
            val votedUser = actual.findUserIdsVotedByUser(1L)
            then("정상적으로 반환한다") {
                votedUser.size shouldBe 3
                votedUser shouldContainAll (listOf(2L, 3L, 4L))
            }
        }

    }

})

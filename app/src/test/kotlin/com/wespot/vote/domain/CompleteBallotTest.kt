package com.wespot.vote.domain

import com.wespot.user.fixture.UserFixture
import com.wespot.vote.CompleteBallot
import com.wespot.vote.fixture.BallotFixture
import com.wespot.vote.fixture.VoteFixture
import com.wespot.voteoption.fixture.VoteOptionFixture
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage

class CompleteBallotTest : BehaviorSpec({

    given("완전한 투표지를 만들 때") {
        val ballot =
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1, 1, 1, 2)
        val validSender = UserFixture.createWithId(1)
        val invalidSender = UserFixture.createWithId(2)
        val validReceiver = UserFixture.createWithId(2)
        val invalidReceiver = UserFixture.createWithId(1)
        val validVote = VoteFixture.createWithIdAndVoteNumberAndBallots(1, 0, emptyList())
        val invalidVote = VoteFixture.createWithIdAndVoteNumberAndBallots(2, 0, emptyList())
        val validVoteOption = VoteOptionFixture.createWithId(1)
        val invalidVoteOption = VoteOptionFixture.createWithId(2)
        `when`("입력된 값이 투표지와 동일하지 않다면") {
            val shouldThrow1 = shouldThrow<IllegalArgumentException> {
                CompleteBallot.of(
                    invalidVote,
                    validVoteOption,
                    validSender,
                    validReceiver,
                    ballot
                )
            }
            val shouldThrow2 = shouldThrow<IllegalArgumentException> {
                CompleteBallot.of(
                    validVote,
                    invalidVoteOption,
                    validSender,
                    validReceiver,
                    ballot
                )
            }
            val shouldThrow3 = shouldThrow<IllegalArgumentException> {
                CompleteBallot.of(
                    validVote,
                    validVoteOption,
                    invalidSender,
                    validReceiver,
                    ballot
                )
            }
            val shouldThrow4 = shouldThrow<IllegalArgumentException> {
                CompleteBallot.of(
                    validVote,
                    validVoteOption,
                    validSender,
                    invalidReceiver,
                    ballot
                )
            }
            then("예외가 발생한다.") {
                shouldThrow1 shouldHaveMessage "입력된 투표가 잘못되었습니다."
                shouldThrow2 shouldHaveMessage "입력된 선택지가 잘못되었습니다."
                shouldThrow3 shouldHaveMessage "입력된 송신자가 잘못되었습니다."
                shouldThrow4 shouldHaveMessage "입력된 수신자가 잘못되었습니다."
            }
        }
        `when`("정상적인 값이 입력된다면") {
            val completeBallot = CompleteBallot.of(validVote, validVoteOption, validSender, validReceiver, ballot)
            then("정상적으로 생성된다.") {
                completeBallot.vote shouldBe validVote
                completeBallot.voteOption shouldBe validVoteOption
                completeBallot.sender shouldBe validSender
                completeBallot.receiver shouldBe validReceiver
                completeBallot.createdAt shouldBe ballot.createdAt
                completeBallot.updatedAt shouldBe ballot.updatedAt
                completeBallot.isReceiverRead shouldBe ballot.isReceiverRead
            }
        }
    }

})

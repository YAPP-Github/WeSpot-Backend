package com.wespot.vote.domain

import com.wespot.vote.VoteMetrics
import com.wespot.vote.fixture.BallotFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class VoteMetricsTest : BehaviorSpec({

    given("초기의 VoteMetrics를") {
        val userId = 1L
        `when`("생성하면") {
            val voteMetrics = VoteMetrics.createInitialState(userId)
            then("정상적으로 생성된다.") {
                voteMetrics.userId shouldBe userId
                voteMetrics.lastVotedDateTime shouldBe LocalDateTime.MIN
                voteMetrics.voteCount shouldBe 0
                voteMetrics.isReceiverRead shouldBe true
            }
        }
    }

    given("기존의 VoteMetrics에") {
        val userId = 1L
        val voteMetrics = VoteMetrics.createInitialState(userId)
        val ballot = BallotFixture.create()
        `when`("최근의 Ballot을 추가하면") {
            ballot.receiverRead()
            val resultVoteMetrics = voteMetrics.recordBallot(ballot)
            then("최근으로 갱신된다.") {
                resultVoteMetrics.userId shouldBe userId
                resultVoteMetrics.lastVotedDateTime shouldBe ballot.createdAt
                resultVoteMetrics.voteCount shouldBe 1
                resultVoteMetrics.isReceiverRead shouldBe true
            }
        }
        `when`("수신자가 읽지 않은 Ballot을 추가하면") {
            val noReceiverReadBallot = BallotFixture.create()
            val resultVoteMetrics = voteMetrics.recordBallot(noReceiverReadBallot)
            then("읽지 않음으로 갱신된다.") {
                resultVoteMetrics.userId shouldBe userId
                resultVoteMetrics.lastVotedDateTime shouldBe noReceiverReadBallot.createdAt
                resultVoteMetrics.voteCount shouldBe 1
                resultVoteMetrics.isReceiverRead shouldBe false
            }
        }
    }

})

package com.wespot.vote.domain

import com.wespot.vote.BallotsAggregator
import com.wespot.vote.fixture.BallotFixture
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import java.time.LocalDateTime

class BallotsAggregatorTest : BehaviorSpec({

    given("투표지 집계기를 생성할 때") {
        `when`("서로 다른 선택지에 대한 결과가 섞여 있으면") {
            then("예외가 발생한다.") {
                val shouldThrow = shouldThrow<IllegalArgumentException> {
                    BallotsAggregator.of(
                        1L,
                        listOf(BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1L, 2L, 1L, 2L))
                    )
                }
                shouldThrow shouldHaveMessage "서로 다른 질문지에 대한 결과가 섞였습니다."
            }
        }
    }

    given("투표지 집계기를 통해") {
        val calculateRankByBallotSize = listOf(
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1L, 1L, 1L, 2L),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1L, 1L, 2L, 3L),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1L, 1L, 3L, 2L),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1L, 1L, 4L, 3L),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1L, 1L, 5L, 2L),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1L, 1L, 6L, 4L),
        )
        `when`("득표수를 토대로") {
            val ballotsAggregator = BallotsAggregator.of(1L, calculateRankByBallotSize)
            val rankedResults = ballotsAggregator.getRankResults()
            then("정렬된 순서를 반환받는다.") {
                rankedResults[0].userId shouldBe 2
                rankedResults[0].voteCount shouldBe 3
                rankedResults[1].userId shouldBe 3
                rankedResults[1].voteCount shouldBe 2
                rankedResults[2].userId shouldBe 4
                rankedResults[2].voteCount shouldBe 1
            }
        }
        val calculateRankByBallotSizeAndReceivedAt = listOf(
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiverAndCreatedAt(
                1L,
                1L,
                1L,
                2L,
                LocalDateTime.now().minusHours(10)
            ),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiverAndCreatedAt(
                1L,
                1L,
                3L,
                2L,
                LocalDateTime.now().minusHours(10)
            ),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiverAndCreatedAt(
                1L,
                1L,
                5L,
                2L,
                LocalDateTime.now().minusHours(10)
            ),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiverAndCreatedAt(
                1L,
                1L,
                2L,
                3L,
                LocalDateTime.now().minusHours(10)
            ),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiverAndCreatedAt(
                1L,
                1L,
                4L,
                3L,
                LocalDateTime.now().minusHours(1)
            ),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiverAndCreatedAt(
                1L,
                1L,
                6L,
                4L,
                LocalDateTime.now().minusHours(2)
            ),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiverAndCreatedAt(
                1L,
                1L,
                7L,
                4L,
                LocalDateTime.now().minusHours(2)
            ),
        )
        `when`("득표수가 같다면, 가장 최근에 받은 투푠을 기준으로") {
            val ballotsAggregator = BallotsAggregator.of(1L, calculateRankByBallotSizeAndReceivedAt)
            val rankedResults = ballotsAggregator.getRankResults()
            then("정렬된 순서를 반환받는다.") {
                rankedResults[0].userId shouldBe 2
                rankedResults[0].voteCount shouldBe 3
                rankedResults[1].userId shouldBe 3
                rankedResults[1].voteCount shouldBe 2
                rankedResults[2].userId shouldBe 4
                rankedResults[2].voteCount shouldBe 2
            }
        }
    }

    given("투표지 집계기를 통해서") {
        val tenHoursAgo = LocalDateTime.now().minusHours(10)
        val nineHoursAgo = LocalDateTime.now().minusHours(9)
        val eightHoursAgo = LocalDateTime.now().minusHours(8)
        val receivedBallots = listOf(
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiverAndCreatedAtAndIsReceiverRead(
                1L,
                1L,
                1L,
                2L,
                tenHoursAgo,
                true
            ),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiverAndCreatedAtAndIsReceiverRead(
                1L,
                1L,
                3L,
                2L,
                tenHoursAgo,
                true
            ),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiverAndCreatedAtAndIsReceiverRead(
                1L,
                1L,
                5L,
                2L,
                eightHoursAgo,
                true
            ),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiverAndCreatedAtAndIsReceiverRead(
                1L,
                1L,
                2L,
                3L,
                tenHoursAgo,
                true
            ),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiverAndCreatedAtAndIsReceiverRead(
                1L,
                1L,
                4L,
                3L,
                nineHoursAgo,
                false
            ),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiverAndCreatedAtAndIsReceiverRead(
                1L,
                1L,
                6L,
                4L,
                tenHoursAgo,
                true
            ),
        )
        `when`("본인이 받은 투표를") {
            val ballotsAggregator = BallotsAggregator.of(1L, receivedBallots)
            val userReceivedVotes = ballotsAggregator.getUserReceivedVotes(2L)
            then("정상적으로 조회한다.") {
                userReceivedVotes.voteCount shouldBe 3
                userReceivedVotes.isReceiverRead shouldBe true
                userReceivedVotes.userId shouldBe 2
                userReceivedVotes.lastVotedDateTime shouldBe eightHoursAgo
            }
        }
        `when`("본인이 받은 투표 중 하나라도 읽지 않은 것이 존재한다면") {
            val ballotsAggregator = BallotsAggregator.of(1L, receivedBallots)
            val userReceivedVotes = ballotsAggregator.getUserReceivedVotes(3L)
            then("읽지 않음을 나타내는 VoteMetrics를 반환한다.") {
                userReceivedVotes.voteCount shouldBe 2
                userReceivedVotes.isReceiverRead shouldBe false
                userReceivedVotes.userId shouldBe 3
                userReceivedVotes.lastVotedDateTime shouldBe nineHoursAgo
            }
        }
    }

})

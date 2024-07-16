package com.wespot.vote.domain

import com.wespot.user.User
import com.wespot.user.fixture.UserFixture
import com.wespot.vote.fixture.BallotFixture
import com.wespot.vote.fixture.VoteFixture
import com.wespot.voteoption.VoteOption
import com.wespot.voteoption.fixture.VoteOptionFixture
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import java.util.*

class VoteTest() : BehaviorSpec({

    given("모든 질문지 중에") {
        val voteOptions = createVoteOptionByCount(9)

        `when`("voteNumber가 0일 때의 오늘의 질문지를") {
            val vote = VoteFixture.createWithVoteNumberAndBallots(0, Collections.emptyList())
            val todayVoteOptions = vote.findTodayVoteOptions(voteOptions)

            then("정상적으로 반환한다.") {
                todayVoteOptions[0].id shouldBe 1
                todayVoteOptions[1].id shouldBe 2
                todayVoteOptions[2].id shouldBe 3
                todayVoteOptions[3].id shouldBe 4
                todayVoteOptions[4].id shouldBe 5
            }
        }

        `when`("voteNumber가 1일 때의 오늘의 질문지를") {
            val vote = VoteFixture.createWithVoteNumberAndBallots(1, Collections.emptyList())
            val todayVoteOptions = vote.findTodayVoteOptions(voteOptions)

            then("정상적으로 반환한다.") {
                todayVoteOptions[0].id shouldBe 6
                todayVoteOptions[1].id shouldBe 7
                todayVoteOptions[2].id shouldBe 8
                todayVoteOptions[3].id shouldBe 9
                todayVoteOptions[4].id shouldBe 1
            }
        }
    }

    given("모든 질문지의 개수가") {
        val voteOptions = createVoteOptionByCount(4)

        `when`("5개 미만인 경우") {
            val vote = VoteFixture.createWithVoteNumberAndBallots(0, Collections.emptyList())
            val throwingCallable = { vote.findTodayVoteOptions(voteOptions) }

            then("예외를 발생시킨다.") {
                val shouldThrow = shouldThrow<IllegalArgumentException>(throwingCallable)
                shouldThrow shouldHaveMessage "선택지는 최소 5개 이상이어야 합니다."
            }
        }
    }

    given("투표를 할 때") {
        val ballots = listOf(
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1L, 1L, 1L, 2L)
        )
        val voteOptions = createVoteOptionByCount(10)
        `when`("중복된 투표를 하는 경우") {
            val vote = VoteFixture.createWithVoteNumberAndBallots(0, ballots)
            val todayVoteOptions = vote.findTodayVoteOptions(voteOptions)
                .stream()
                .map { it.id!! }
                .toList()

            then("예외가 발생한다.") {
                val shouldThrow = shouldThrow<IllegalArgumentException> {
                    vote.addBallot(
                        todayVoteOptions,
                        1L,
                        1L,
                        2L
                    )
                }
                shouldThrow shouldHaveMessage "하루에 한 명의 회원에게 한 개의 투표만 할 수 있습니다."
            }
        }

        `when`("중복되지 않은 투표를 하는 경우") {
            val vote = VoteFixture.createWithVoteNumberAndBallots(0, ballots)
            val todayVoteOptions = vote.findTodayVoteOptions(voteOptions)
                .stream()
                .map { it.id!! }
                .toList()

            then("정상적으로 투표가 진행된다.") {
                shouldNotThrow<IllegalArgumentException> {
                    vote.addBallot(
                        todayVoteOptions,
                        1L,
                        1L,
                        3L
                    )
                }
            }
        }

        `when`("오늘의 질문지가 아닌 질문지를 선택한 경우") {
            val vote = VoteFixture.createWithVoteNumberAndBallots(0, ballots)
            val todayVoteOptions = vote.findTodayVoteOptions(voteOptions)
                .stream()
                .map { it.id!! }
                .toList()

            then("예외가 발생한다.") {
                val shouldThrow = shouldThrow<IllegalArgumentException> {
                    vote.addBallot(
                        todayVoteOptions,
                        6L,
                        1L,
                        3L
                    )
                }
                shouldThrow shouldHaveMessage "오늘 제공된 질문지만 선택해 투표할 수 있습니다."
            }
        }
    }

    given("현재 사용자가 아직 투표하지 인원 중에") {
        val ballots = listOf(
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1L, 1L, 1L, 2L)
        )
        `when`("5명 이하의 학생들을") {
            val users = createUserOptionByCount(5)
            val vote = VoteFixture.createWithVoteNumberAndBallots(0, ballots)
            val me = users[0]
            val voteUsers = vote.findUsersForVote(users, me)
            then("정상적으로 반환한다.") {
                voteUsers.size shouldBe 3
                voteUsers[0].id shouldBe 3
                voteUsers[1].id shouldBe 4
                voteUsers[2].id shouldBe 5
            }
        }

        `when`("최대 5명의 학생들을") {
            val users = createUserOptionByCount(8)
            val vote = VoteFixture.createWithVoteNumberAndBallots(0, ballots)
            val me = users[0]
            val voteUsers = vote.findUsersForVote(users, me)
            then("정상적으로 반환한다.") {
                voteUsers.size shouldBe 5
                voteUsers[0].id shouldBe 3
                voteUsers[1].id shouldBe 4
                voteUsers[2].id shouldBe 5
                voteUsers[3].id shouldBe 6
                voteUsers[4].id shouldBe 7
            }
        }

    }

})

private fun createVoteOptionByCount(voteOptionCount: Long): List<VoteOption> {
    val voteOptions = ArrayList<VoteOption>()
    for (id in 1..voteOptionCount) {
        voteOptions.add(VoteOptionFixture.createWithId(id))
    }
    return voteOptions
}

private fun createUserOptionByCount(userCount: Long): List<User> {
    val users = ArrayList<User>()
    for (id in 1..userCount) {
        users.add(UserFixture.createWithId(id))
    }
    return users
}

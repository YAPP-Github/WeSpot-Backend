package com.wespot.vote.domain

import com.wespot.user.User
import com.wespot.user.fixture.UserFixture
import com.wespot.vote.RankCalculateService
import com.wespot.vote.fixture.BallotFixture
import com.wespot.vote.fixture.VoteFixture
import com.wespot.voteoption.VoteOption
import com.wespot.voteoption.fixture.VoteOptionFixture
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import java.time.LocalDateTime
import java.util.*

class VoteTest() : BehaviorSpec({

    given("모든 질문지 중에") {
        val voteOptions = createVoteOptionByCount(9)

        `when`("voteNumber가 0일 때의 오늘의 질문지를") {
            val vote = VoteFixture.createWithVoteNumberAndBallots(0, Collections.emptyList())
            val todayVoteOptions = vote.findVoteOptionsByVoteDate(voteOptions).voteOptions

            then("정상적으로 반환한다.") {
                todayVoteOptions[0].id shouldBe 1
                todayVoteOptions[1].id shouldBe 2
                todayVoteOptions[2].id shouldBe 3
                todayVoteOptions[3].id shouldBe 4
                todayVoteOptions[4].id shouldBe 5
            }
        }

        `when`("오늘의 질문지를 뽑아낼 때, 범위를 벗어나는 경우") {
            val vote = VoteFixture.createWithVoteNumberAndBallots(1, Collections.emptyList())
            val throwingCallable = { vote.findVoteOptionsByVoteDate(voteOptions) }

            then("예외가 발생한다.") {
                val shouldThrow = shouldThrow<IllegalArgumentException>(throwingCallable)
                shouldThrow shouldHaveMessage "선택지의 개수가 5의 배수가 아닙니다."
            }
        }
    }

    given("모든 질문지의 개수가") {
        val voteOptions = createVoteOptionByCount(4)

        `when`("5개 미만인 경우") {
            val vote = VoteFixture.createWithVoteNumberAndBallots(0, Collections.emptyList())
            val throwingCallable = { vote.findVoteOptionsByVoteDate(voteOptions) }

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
            val todayVoteOptions = vote.findVoteOptionsByVoteDate(voteOptions)

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
            val todayVoteOptions = vote.findVoteOptionsByVoteDate(voteOptions)

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
            val todayVoteOptions = vote.findVoteOptionsByVoteDate(voteOptions)

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

    given("투표 결과를 통해") {
        val ballots = listOf(
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiverAndCreatedAt(
                1L,
                1L,
                2L,
                1L,
                LocalDateTime.now().minusHours(10)
            ),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiverAndCreatedAt(
                1L,
                1L,
                3L,
                2L,
                LocalDateTime.now().minusHours(8)
            ),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1L, 2L, 1L, 3L),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1L, 3L, 5L, 4L),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1L, 4L, 4L, 6L),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1L, 6L, 4L, 5L),
        )

        `when`("등수를 집계할 때, 존재하지 않는 유저의 통계는") {
            val users = createUserOptionByCount(5)
            val voteOptions = createVoteOptionByCount(10)
            val vote = VoteFixture.createWithVoteNumberAndBallots(0, ballots)
            val voteOptionsByVoteDate = vote.findVoteOptionsByVoteDate(voteOptions)
            val rankedVoteResults = vote.getRankedVoteResults(voteOptionsByVoteDate, users, RankCalculateService())

            then("집계하지 않는다.") {
                val forthVoteOption = voteOptions[3]
                rankedVoteResults[forthVoteOption]!!.size shouldBe 0
            }
        }

        `when`("등수를 집계할 때, 존재하지 않는 VoteOption에 대해") {
            val users = createUserOptionByCount(5)
            val voteOptions = createVoteOptionByCount(10)
            val vote = VoteFixture.createWithVoteNumberAndBallots(0, ballots)
            val voteOptionsByVoteDate = vote.findVoteOptionsByVoteDate(voteOptions)
            val rankedVoteResults = vote.getRankedVoteResults(voteOptionsByVoteDate, users, RankCalculateService())

            then("집계하지 않는다.") {
                rankedVoteResults.containsKey(voteOptions[5]) shouldBe false
            }
        }

        `when`("등수를 집계할 때, VoteOption의 Id로") {
            val users = createUserOptionByCount(5)
            val voteOptions = createVoteOptionByCount(10)
            val vote = VoteFixture.createWithVoteNumberAndBallots(0, ballots)
            val voteOptionsByVoteDate = vote.findVoteOptionsByVoteDate(voteOptions)
            val rankedVoteResults = vote.getRankedVoteResults(voteOptionsByVoteDate, users, RankCalculateService())

            then("정렬되어 집계한다.") {
                val entries = rankedVoteResults.toList()
                entries[0].first.id shouldBe 1
                entries[1].first.id shouldBe 2
                entries[2].first.id shouldBe 3
                entries[3].first.id shouldBe 4
                entries[4].first.id shouldBe 5
            }
        }

        `when`("등수를 집계할 때, 주어진 User를 매핑해서") {
            val users = createUserOptionByCount(5)
            val voteOptions = createVoteOptionByCount(10)
            val vote = VoteFixture.createWithVoteNumberAndBallots(0, ballots)
            val voteOptionsByVoteDate = vote.findVoteOptionsByVoteDate(voteOptions)
            val rankedVoteResults = vote.getRankedVoteResults(voteOptionsByVoteDate, users, RankCalculateService())

            then("결과를 반환한다.") {
                val entries = rankedVoteResults.toList()
                entries[0].second.size shouldBe 2
                entries[0].second[0].user.id shouldBe 2
                entries[0].second[1].user.id shouldBe 1
                entries[1].second.size shouldBe 1
                entries[1].second[0].user.id shouldBe 3
                entries[2].second.size shouldBe 1
                entries[2].second[0].user.id shouldBe 4
                entries[3].second.size shouldBe 0
                entries[4].second.size shouldBe 0
            }
        }

        `when`("등수를 집계할 때, 주어진 VoteOption을 매핑해서") {
            val users = createUserOptionByCount(5)
            val voteOptions = createVoteOptionByCount(10)
            val vote = VoteFixture.createWithVoteNumberAndBallots(0, ballots)
            val voteOptionsByVoteDate = vote.findVoteOptionsByVoteDate(voteOptions)
            val rankedVoteResults = vote.getRankedVoteResults(voteOptionsByVoteDate, users, RankCalculateService())

            then("결과를 반환한다.") {
                val entries = rankedVoteResults.toList()
                entries[0].first shouldBe voteOptionsByVoteDate.voteOptions[0]
                entries[1].first shouldBe voteOptionsByVoteDate.voteOptions[1]
                entries[2].first shouldBe voteOptionsByVoteDate.voteOptions[2]
                entries[3].first shouldBe voteOptionsByVoteDate.voteOptions[3]
                entries[4].first shouldBe voteOptionsByVoteDate.voteOptions[4]
            }
        }

        `when`("등수를 집계할 때, 등수도 함께 포함해서") {
            val users = createUserOptionByCount(5)
            val voteOptions = createVoteOptionByCount(10)
            val vote = VoteFixture.createWithVoteNumberAndBallots(0, ballots)
            val voteOptionsByVoteDate = vote.findVoteOptionsByVoteDate(voteOptions)
            val rankedVoteResults = vote.getRankedVoteResults(voteOptionsByVoteDate, users, RankCalculateService())

            then("결과를 반환한다.") {
                val entries = rankedVoteResults.toList()
                entries[0].second.size shouldBe 2
                entries[0].second[0].user.id shouldBe 2
                entries[0].second[0].rate.value shouldBe Int.MAX_VALUE
                entries[0].second[1].user.id shouldBe 1
                entries[0].second[1].rate.value shouldBe Int.MAX_VALUE
                entries[1].second.size shouldBe 1
                entries[1].second[0].user.id shouldBe 3
                entries[1].second[0].rate.value shouldBe Int.MAX_VALUE
                entries[2].second.size shouldBe 1
                entries[2].second[0].user.id shouldBe 4
                entries[2].second[0].rate.value shouldBe Int.MAX_VALUE
                entries[3].second.size shouldBe 0
                entries[4].second.size shouldBe 0
            }
        }
    }

    given("본인이 받은 투표") {
        val ballots = listOf(
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiverAndCreatedAt(
                1L,
                1L,
                2L,
                1L,
                LocalDateTime.now().minusHours(10)
            ),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiverAndCreatedAt(
                1L,
                1L,
                3L,
                1L,
                LocalDateTime.now().minusHours(8)
            ),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiverAndCreatedAt(
                1L,
                2L,
                4L,
                1L,
                LocalDateTime.now().minusHours(8)
            ),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1L, 2L, 1L, 2L),
        )
        `when`("목록을 조회하는 경우") {
            val users = createUserOptionByCount(5)
            val voteOptions = createVoteOptionByCount(10)
            val vote = VoteFixture.createWithVoteNumberAndBallots(0, ballots)
            val voteOptionsByVoteDate = vote.findVoteOptionsByVoteDate(voteOptions)
            val receivedVotes = vote.getUserReceivedVotes(voteOptionsByVoteDate, users[0])

            then("결과를 정상적으로 반환한다.") {
                receivedVotes.size shouldBe 2
                receivedVotes[voteOptions[0]]!!.user shouldBe users[0]
                receivedVotes[voteOptions[0]]!!.voteCount shouldBe 2
                receivedVotes[voteOptions[0]]!!.rate.value shouldBe Int.MAX_VALUE
                receivedVotes[voteOptions[0]]!!.isReceiverRead shouldBe false
                receivedVotes[voteOptions[1]]!!.user shouldBe users[0]
                receivedVotes[voteOptions[1]]!!.voteCount shouldBe 1
                receivedVotes[voteOptions[1]]!!.rate.value shouldBe Int.MAX_VALUE
                receivedVotes[voteOptions[1]]!!.isReceiverRead shouldBe false
            }
        }

        `when`("개별 조회하는 경우, 오늘의 질문이 아니면") {
            val users = createUserOptionByCount(5)
            val voteOptions = createVoteOptionByCount(10)
            val vote = VoteFixture.createWithVoteNumberAndBallots(0, ballots)
            val voteOptionsByVoteDate = vote.findVoteOptionsByVoteDate(voteOptions)

            then("예외가 발생한다.") {
                val shouldThrow = shouldThrow<IllegalArgumentException> {
                    vote.getUserReceivedVote(
                        voteOptionsByVoteDate,
                        voteOptions[5],
                        users[0]
                    )
                }
                shouldThrow shouldHaveMessage "오늘 제공된 질문지만 선택해 투표할 수 있습니다."
            }
        }
        `when`("개별 조회하는 경우") {
            val users = createUserOptionByCount(5)
            val voteOptions = createVoteOptionByCount(10)
            val vote = VoteFixture.createWithVoteNumberAndBallots(0, ballots)
            val voteOptionsByVoteDate = vote.findVoteOptionsByVoteDate(voteOptions)
            val userReceivedVoteByFirstVoteOption =
                vote.getUserReceivedVote(voteOptionsByVoteDate, voteOptions[0], users[0])
            val userReceivedVoteBySecondVoteOption =
                vote.getUserReceivedVote(voteOptionsByVoteDate, voteOptions[1], users[0])

            then("결과를 정상적으로 반환한다.") {
                userReceivedVoteByFirstVoteOption.user shouldBe users[0]
                userReceivedVoteByFirstVoteOption.voteCount shouldBe 2
                userReceivedVoteByFirstVoteOption.rate.value shouldBe 1
                userReceivedVoteByFirstVoteOption.isReceiverRead shouldBe false
                userReceivedVoteByFirstVoteOption.lastVotedDateTime shouldBe ballots[1].createdAt
                userReceivedVoteBySecondVoteOption.user shouldBe users[0]
                userReceivedVoteBySecondVoteOption.voteCount shouldBe 1
                userReceivedVoteBySecondVoteOption.rate.value shouldBe 2
                userReceivedVoteBySecondVoteOption.isReceiverRead shouldBe false
                userReceivedVoteBySecondVoteOption.lastVotedDateTime shouldBe ballots[2].createdAt
                ballots[0].isReceiverRead shouldBe true
                ballots[1].isReceiverRead shouldBe true
                ballots[2].isReceiverRead shouldBe true
            }

            val receiverReadVoteByFirstVoteOption =
                vote.getUserReceivedVote(voteOptionsByVoteDate, voteOptions[0], users[0])
            val receiverReadVoteBySecondVoteOption =
                vote.getUserReceivedVote(voteOptionsByVoteDate, voteOptions[1], users[0])
            then("수신자가 읽은 것으로 변경된다.") {
                receiverReadVoteByFirstVoteOption.isReceiverRead shouldBe true
                receiverReadVoteBySecondVoteOption.isReceiverRead shouldBe true
            }
        }
    }

    given("본인이 보낸 투표") {
        val ballots = listOf(
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1L, 1L, 1L, 2L),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1L, 1L, 1L, 3L),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1L, 2L, 1L, 4L),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1L, 3L, 1L, 5L),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1L, 1L, 2L, 1L),
        )
        `when`("목록을 조회하는 경우") {
            val users = createUserOptionByCount(5)
            val voteOptions = createVoteOptionByCount(10)
            val vote = VoteFixture.createWithVoteNumberAndBallots(0, ballots)
            val voteOptionsByVoteDate = vote.findVoteOptionsByVoteDate(voteOptions)
            val userReceivedVote = vote.getUserSentVotes(voteOptionsByVoteDate, users[0])

            then("결과를 정상적으로 반환한다.") {
                userReceivedVote.size shouldBe 3
                userReceivedVote[voteOptions[0]]!!.size shouldBe 2
                userReceivedVote[voteOptions[0]]!!.size shouldBe 2
                userReceivedVote[voteOptions[0]]!![0].receiverId shouldBe 2
                userReceivedVote[voteOptions[0]]!![0].senderId shouldBe 1
                userReceivedVote[voteOptions[0]]!![1].receiverId shouldBe 3
                userReceivedVote[voteOptions[0]]!![1].senderId shouldBe 1
                userReceivedVote[voteOptions[1]]!!.size shouldBe 1
                userReceivedVote[voteOptions[1]]!![0].senderId shouldBe 1
                userReceivedVote[voteOptions[1]]!![0].receiverId shouldBe 4
                userReceivedVote[voteOptions[2]]!!.size shouldBe 1
                userReceivedVote[voteOptions[2]]!![0].senderId shouldBe 1
                userReceivedVote[voteOptions[2]]!![0].receiverId shouldBe 5
            }
        }

        `when`("개별 조회하는 경우, 오늘의 질문지가 아니면") {
            val users = createUserOptionByCount(5)
            val voteOptions = createVoteOptionByCount(10)
            val vote = VoteFixture.createWithVoteNumberAndBallots(0, ballots)
            val voteOptionsByVoteDate = vote.findVoteOptionsByVoteDate(voteOptions)

            then("예외가 발생한다.") {
                val shouldThrow = shouldThrow<IllegalArgumentException> {
                    vote.getUserSentVote(
                        voteOptionsByVoteDate,
                        voteOptions[5],
                        users[0]
                    )
                }
                shouldThrow shouldHaveMessage "오늘 제공된 질문지만 선택해 투표할 수 있습니다."
            }
        }
        `when`("개별 조회하는 경우") {
            val users = createUserOptionByCount(5)
            val voteOptions = createVoteOptionByCount(10)
            val vote = VoteFixture.createWithVoteNumberAndBallots(0, ballots)
            val voteOptionsByVoteDate = vote.findVoteOptionsByVoteDate(voteOptions)
            val userSentVoteByFirstVoteOption = vote.getUserSentVote(voteOptionsByVoteDate, voteOptions[0], users[0])
            val userSentVoteBySecondVoteOption = vote.getUserSentVote(voteOptionsByVoteDate, voteOptions[1], users[0])

            then("결과를 정상적으로 반환한다.") {
                userSentVoteByFirstVoteOption.size shouldBe 2
                userSentVoteByFirstVoteOption[0].voteOptionId shouldBe 1
                userSentVoteByFirstVoteOption[0].senderId shouldBe 1
                userSentVoteByFirstVoteOption[0].receiverId shouldBe 2
                userSentVoteByFirstVoteOption[1].voteOptionId shouldBe 1
                userSentVoteByFirstVoteOption[1].senderId shouldBe 1
                userSentVoteByFirstVoteOption[1].receiverId shouldBe 3
                userSentVoteBySecondVoteOption.size shouldBe 1
                userSentVoteBySecondVoteOption[0].voteOptionId shouldBe 2
                userSentVoteBySecondVoteOption[0].senderId shouldBe 1
                userSentVoteBySecondVoteOption[0].receiverId shouldBe 4
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

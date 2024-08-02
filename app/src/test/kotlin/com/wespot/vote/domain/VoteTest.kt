package com.wespot.vote.domain

import com.wespot.user.User
import com.wespot.user.fixture.UserFixture
import com.wespot.vote.RankCalculateService
import com.wespot.vote.ReceivedVoteCalculateService
import com.wespot.vote.Vote
import com.wespot.vote.VoteIdentifier
import com.wespot.vote.fixture.BallotFixture
import com.wespot.vote.fixture.VoteFixture
import com.wespot.voteoption.VoteOption
import com.wespot.voteoption.fixture.VoteOptionFixture
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import java.time.LocalDate
import java.time.LocalDateTime

class VoteTest() : BehaviorSpec({

    given("모든 질문지 중에") {
        val voteOptions = createVoteOptionByCount(9)
        val voteIdentifier =
            VoteIdentifier.of(UserFixture.createWithSchoolIdAndGradeAndClassNumber(1L, 1, 1), LocalDate.now())

        `when`("voteNumber가 0일 때의 오늘의 질문지를") {
            val vote = Vote.of(voteIdentifier, voteOptions, null)
            val todayVoteOptions = vote.voteOptionsByVoteDate.voteOptionsByVoteDate

            then("정상적으로 반환한다.") {
                todayVoteOptions[0].voteOption.id shouldBe 1
                todayVoteOptions[1].voteOption.id shouldBe 2
                todayVoteOptions[2].voteOption.id shouldBe 3
                todayVoteOptions[3].voteOption.id shouldBe 4
                todayVoteOptions[4].voteOption.id shouldBe 5
            }
        }

        `when`("오늘의 질문지를 뽑아낼 때, 범위를 벗어나는 경우") {
            val yesterdayVoteIdentifier =
                VoteIdentifier.of(
                    UserFixture.createWithSchoolIdAndGradeAndClassNumber(1L, 1, 1),
                    LocalDate.now().minusDays(1)
                )
            val vote = Vote.of(yesterdayVoteIdentifier, voteOptions, null)
            val throwingCallable = { Vote.of(voteIdentifier, voteOptions, Vote.of(voteIdentifier, voteOptions, vote)) }

            then("예외가 발생한다.") {
                val shouldThrow = shouldThrow<IllegalArgumentException>(throwingCallable)
                shouldThrow shouldHaveMessage "선택지의 개수가 5의 배수가 아닙니다."
            }
        }
    }

    given("모든 질문지의 개수가") {
        val voteOptions = createVoteOptionByCount(4)
        val voteIdentifier =
            VoteIdentifier.of(UserFixture.createWithSchoolIdAndGradeAndClassNumber(1L, 1, 1), LocalDate.now())

        `when`("5개 미만인 경우") {
            val throwingCallable = {
                Vote.of(voteIdentifier, voteOptions, null)
            }

            then("예외를 발생시킨다.") {
                val shouldThrow = shouldThrow<IllegalArgumentException>(throwingCallable)
                shouldThrow shouldHaveMessage "선택지는 최소 5개 이상이어야 합니다."
            }
        }
    }

    given("투표를 할 때") {
        val voteOptions = createVoteOptionByCount(10)
        val voteIdentifier =
            VoteIdentifier.of(UserFixture.createWithSchoolIdAndGradeAndClassNumber(1L, 1, 1), LocalDate.now())
        `when`("중복된 투표를 하는 경우") {
            val vote = Vote.of(voteIdentifier, voteOptions, null)
            vote.addBallot(1, 1, 2, LocalDateTime.now())

            then("예외가 발생한다.") {
                val shouldThrow = shouldThrow<IllegalArgumentException> {
                    vote.addBallot(
                        1L,
                        1L,
                        2L,
                        LocalDateTime.now()
                    )
                }
                shouldThrow shouldHaveMessage "하루에 한 명의 회원에게 한 개의 투표만 할 수 있습니다."
            }
        }

        `when`("중복되지 않은 투표를 하는 경우") {
            val vote = Vote.of(voteIdentifier, voteOptions, null)
            vote.addBallot(1, 1, 2, LocalDateTime.now())

            then("정상적으로 투표가 진행된다.") {
                shouldNotThrow<IllegalArgumentException> {
                    vote.addBallot(
                        1L,
                        1L,
                        3L,
                        LocalDateTime.now()
                    )
                }
            }
        }

        `when`("오늘의 질문지가 아닌 질문지를 선택한 경우") {
            val vote = Vote.of(voteIdentifier, voteOptions, null)
            vote.addBallot(1, 1, 2, LocalDateTime.now())

            then("예외가 발생한다.") {
                val shouldThrow = shouldThrow<IllegalArgumentException> {
                    vote.addBallot(
                        6L,
                        1L,
                        3L,
                        LocalDateTime.now()
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
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1L, 1L, 2L, 1L),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1L, 1L, 3L, 2L),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1L, 2L, 1L, 3L),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1L, 3L, 5L, 4L),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1L, 4L, 4L, 6L),
        )
        val voteIdentifier =
            VoteIdentifier.of(UserFixture.createWithSchoolIdAndGradeAndClassNumber(1, 1, 1), LocalDate.now())

        `when`("등수를 집계할 때, 존재하지 않는 유저의 통계는") {
            val users = createUserOptionByCount(5)
            val voteOptions = createVoteOptionByCount(10)
            val vote = Vote.of(voteIdentifier, voteOptions, null)
            ballots.forEach { vote.addBallot(it.voteOptionId, it.senderId, it.receiverId, LocalDateTime.now()) }
            val rankedVoteResults = vote.getRankedVoteResults(users, RankCalculateService())

            then("집계하지 않는다.") {
                val forthVoteOption = voteOptions[3]
                rankedVoteResults[forthVoteOption]!!.size shouldBe 0
            }
        }

        `when`("등수를 집계할 때, 존재하지 않는 VoteOption에 대해") {
            val users = createUserOptionByCount(5)
            val voteOptions = createVoteOptionByCount(10)
            val vote = Vote.of(voteIdentifier, voteOptions, null)
            ballots.forEach { vote.addBallot(it.voteOptionId, it.senderId, it.receiverId, LocalDateTime.now()) }
            val rankedVoteResults = vote.getRankedVoteResults(users, RankCalculateService())

            then("집계하지 않는다.") {
                rankedVoteResults.containsKey(voteOptions[5]) shouldBe false
            }
        }

        `when`("등수를 집계할 때, VoteOption의 Id로") {
            val users = createUserOptionByCount(5)
            val voteOptions = createVoteOptionByCount(10)
            val vote = Vote.of(voteIdentifier, voteOptions, null)
            ballots.forEach { vote.addBallot(it.voteOptionId, it.senderId, it.receiverId, LocalDateTime.now()) }
            val rankedVoteResults = vote.getRankedVoteResults(users, RankCalculateService())

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
            val vote = Vote.of(voteIdentifier, voteOptions, null)
            ballots.forEach { vote.addBallot(it.voteOptionId, it.senderId, it.receiverId, LocalDateTime.now()) }
            val rankedVoteResults = vote.getRankedVoteResults(users, RankCalculateService())

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
            val vote = Vote.of(voteIdentifier, voteOptions, null)
            ballots.forEach { vote.addBallot(it.voteOptionId, it.senderId, it.receiverId, LocalDateTime.now()) }
            val rankedVoteResults = vote.getRankedVoteResults(users, RankCalculateService())

            then("결과를 반환한다.") {
                val entries = rankedVoteResults.toList()
                entries[0].first shouldBe vote.voteOptionsByVoteDate.voteOptionsByVoteDate[0].voteOption
                entries[1].first shouldBe vote.voteOptionsByVoteDate.voteOptionsByVoteDate[1].voteOption
                entries[2].first shouldBe vote.voteOptionsByVoteDate.voteOptionsByVoteDate[2].voteOption
                entries[3].first shouldBe vote.voteOptionsByVoteDate.voteOptionsByVoteDate[3].voteOption
                entries[4].first shouldBe vote.voteOptionsByVoteDate.voteOptionsByVoteDate[4].voteOption
            }
        }

        `when`("등수를 집계할 때, 등수도 함께 포함해서") {
            val users = createUserOptionByCount(5)
            val voteOptions = createVoteOptionByCount(10)
            val vote = Vote.of(voteIdentifier, voteOptions, null)
            ballots.forEach { vote.addBallot(it.voteOptionId, it.senderId, it.receiverId, LocalDateTime.now()) }
            val rankedVoteResults = vote.getRankedVoteResults(users, RankCalculateService())

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
                LocalDateTime.now().minusSeconds(2)
            ),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiverAndCreatedAt(
                1L,
                1L,
                3L,
                1L,
                LocalDateTime.now().minusSeconds(1)
            ),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiverAndCreatedAt(
                1L,
                2L,
                4L,
                1L,
                LocalDateTime.now().minusSeconds(1)
            ),
            BallotFixture.createByVoteAndVoteOptionAndSenderAndReceiver(1L, 2L, 1L, 2L),
        )
        val voteIdentifier =
            VoteIdentifier.of(UserFixture.createWithSchoolIdAndGradeAndClassNumber(1, 1, 1), LocalDate.now())

        `when`("목록을 조회하는 경우") {
            val users = createUserOptionByCount(5)
            val voteOptions = createVoteOptionByCount(10)
            val vote = Vote.of(voteIdentifier, voteOptions, null)
            ballots.forEach { vote.addBallot(it.voteOptionId, it.senderId, it.receiverId, it.createdAt) }
            val receivedVotes = vote.getUserReceivedVotes(users[0], ReceivedVoteCalculateService())

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
            val vote = Vote.of(voteIdentifier, voteOptions, null)
            ballots.forEach { vote.addBallot(it.voteOptionId, it.senderId, it.receiverId, it.createdAt) }

            then("예외가 발생한다.") {
                val shouldThrow = shouldThrow<IllegalArgumentException> {
                    vote.getUserReceivedVote(
                        voteOptions[5],
                        users[0],
                        ReceivedVoteCalculateService()
                    )
                }
                shouldThrow shouldHaveMessage "오늘 제공된 질문지만 선택해 투표할 수 있습니다."
            }
        }
        `when`("개별 조회하는 경우") {
            val users = createUserOptionByCount(5)
            val voteOptions = createVoteOptionByCount(10)
            val vote = Vote.of(voteIdentifier, voteOptions, null)
            ballots.forEach { vote.addBallot(it.voteOptionId, it.senderId, it.receiverId, it.createdAt) }
            val userReceivedVoteByFirstVoteOption =
                vote.getUserReceivedVote(
                    voteOptions[0],
                    users[0],
                    ReceivedVoteCalculateService()
                )
            val userReceivedVoteBySecondVoteOption =
                vote.getUserReceivedVote(
                    voteOptions[1],
                    users[0],
                    ReceivedVoteCalculateService()
                )

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
            }

            val receiverReadVoteByFirstVoteOption =
                vote.getUserReceivedVote(
                    voteOptions[0],
                    users[0],
                    ReceivedVoteCalculateService()
                )
            val receiverReadVoteBySecondVoteOption =
                vote.getUserReceivedVote(
                    voteOptions[1],
                    users[0],
                    ReceivedVoteCalculateService()
                )
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
        val voteIdentifier =
            VoteIdentifier.of(UserFixture.createWithSchoolIdAndGradeAndClassNumber(1, 1, 1), LocalDate.now())

        `when`("목록을 조회하는 경우") {
            val users = createUserOptionByCount(5)
            val voteOptions = createVoteOptionByCount(10)
            val vote = Vote.of(voteIdentifier, voteOptions, null)
            ballots.forEach { vote.addBallot(it.voteOptionId, it.senderId, it.receiverId, it.createdAt) }
            val userReceivedVote = vote.getUserSentVotes(users[0])

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
            val vote = Vote.of(voteIdentifier, voteOptions, null)
            ballots.forEach { vote.addBallot(it.voteOptionId, it.senderId, it.receiverId, it.createdAt) }

            then("예외가 발생한다.") {
                val shouldThrow = shouldThrow<IllegalArgumentException> {
                    vote.getUserSentVote(
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
            val vote = Vote.of(voteIdentifier, voteOptions, null)
            ballots.forEach { vote.addBallot(it.voteOptionId, it.senderId, it.receiverId, it.createdAt) }
            val userSentVoteByFirstVoteOption = vote.getUserSentVote(voteOptions[0], users[0])
            val userSentVoteBySecondVoteOption = vote.getUserSentVote(voteOptions[1], users[0])

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

    given("초기 상태의 투표함을 만들 때,") {
        val voteOptions = createVoteOptionByCount(10)
        `when`("이전 투표가 없으면") {
            val user = UserFixture.createWithSchoolIdAndGradeAndClassNumber(1, 1, 1)
            val now = LocalDate.now()
            val voteIdentifier = VoteIdentifier.of(user, now)
            val vote = Vote.of(voteIdentifier, voteOptions, null)

            then("VoteNumber가 0으로 설정된다.") {
                vote.voteIdentifier.date shouldBe voteIdentifier.date
                vote.voteIdentifier.schoolId shouldBe user.schoolId
                vote.voteIdentifier.grade shouldBe user.grade
                vote.voteIdentifier.classNumber shouldBe user.classNumber
                vote.voteNumber shouldBe 0
            }
        }
        `when`("이전 투표가 존재하면") {
            val user = UserFixture.createWithSchoolIdAndGradeAndClassNumber(1, 1, 1)
            val yesterday = LocalDate.now().minusDays(1)
            val voteIdentifier = VoteIdentifier.of(user, yesterday)
            val previousVote = Vote.of(voteIdentifier, voteOptions, null)
            val newVoteIdentifier = VoteIdentifier.of(user, yesterday.plusDays(1))
            val vote = Vote.of(newVoteIdentifier, voteOptions, previousVote)

            then("VoteNumber가 이전 투표의 VoteNumber + 1 로 설정된다.") {
                vote.voteIdentifier.date shouldBe newVoteIdentifier.date
                vote.voteIdentifier.schoolId shouldBe user.schoolId
                vote.voteIdentifier.grade shouldBe user.grade
                vote.voteIdentifier.classNumber shouldBe user.classNumber
                vote.voteNumber shouldBe 1
            }
        }

        `when`("입력된 학급과 PreviousVote의 학급이 동일하지 않을 때") {
            val firstUser = UserFixture.createWithSchoolIdAndGradeAndClassNumber(1, 1, 1)
            val secondUser = UserFixture.createWithSchoolIdAndGradeAndClassNumber(1, 1, 2)
            val now = LocalDate.now()
            val firstUserVoteIdentifier = VoteIdentifier.of(firstUser, now.minusDays(1))
            val previousVote = Vote.of(firstUserVoteIdentifier, voteOptions, null)
            val secondUserVoteIdentifier = VoteIdentifier.of(secondUser, now)
            val shouldThrow =
                shouldThrow<IllegalArgumentException> { Vote.of(secondUserVoteIdentifier, voteOptions, previousVote) }

            then("예외가 발생한다.") {
                shouldThrow shouldHaveMessage "입력된 이전 투표가 유효하지 않습니다."
            }
        }

        `when`("입력된 날짜의 어제가 previousVote의 date가 아니면") {
            val firstUser = UserFixture.createWithSchoolIdAndGradeAndClassNumber(1, 1, 1)
            val secondUser = UserFixture.createWithSchoolIdAndGradeAndClassNumber(1, 1, 1)
            val now = LocalDate.now()
            val firstUserVoteIdentifier = VoteIdentifier.of(firstUser, now.minusDays(2))
            val previousVote = Vote.of(firstUserVoteIdentifier, voteOptions, null)
            val secondUserVoteIdentifier = VoteIdentifier.of(secondUser, now)
            val shouldThrow =
                shouldThrow<IllegalArgumentException> { Vote.of(secondUserVoteIdentifier, voteOptions, previousVote) }

            then("예외가 발생한다.") {
                shouldThrow shouldHaveMessage "입력된 이전 투표가 유효하지 않습니다."
            }
        }
    }

    // 보낸 이의 수를 카운트
    given("") {
        `when`("") {
            then("") {
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

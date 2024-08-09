package com.wespot.vote.service

import com.wespot.common.service.ServiceTest
import com.wespot.user.entity.UserJpaEntity
import com.wespot.user.fixture.UserFixture
import com.wespot.user.mapper.UserMapper
import com.wespot.user.repository.UserJpaRepository
import com.wespot.vote.Ballot
import com.wespot.vote.BallotJpaRepository
import com.wespot.vote.BallotMapper
import com.wespot.vote.Vote
import com.wespot.vote.VoteIdentifier
import com.wespot.vote.port.out.VotePort
import com.wespot.voteoption.VoteOptionJpaEntity
import com.wespot.voteoption.VoteOptionJpaRepository
import com.wespot.voteoption.VoteOptionMapper
import com.wespot.voteoption.fixture.VoteOptionFixture
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.time.LocalDateTime

class ReceivedVoteServiceTest @Autowired constructor(
    private var receivedVoteService: ReceivedVoteService,
    private var userJpaRepository: UserJpaRepository,
    private var voteOptionJpaRepository: VoteOptionJpaRepository,
    private var ballotJpaRepository: BallotJpaRepository,
    private var votePort: VotePort,
) : ServiceTest() {

    private var users: MutableList<UserJpaEntity> = mutableListOf()
    private var voteOptions: MutableList<VoteOptionJpaEntity> = mutableListOf()
    private var vote1: Vote? = null
    private var vote2: Vote? = null

    @BeforeEach
    fun setUp() {
        voteOptions.clear()
        users.clear()
        for (i in 0 until 10) {
            val userJpaEntity = UserMapper.mapToJpaEntity(UserFixture.createWithIdAndEmail(0, "TestEmail${i}@Kakako"))
            users.add(userJpaRepository.save(userJpaEntity))
            val voteOptionJpaEntity =
                VoteOptionMapper.mapToJpaEntity(VoteOptionFixture.createWithId(0))
            voteOptions.add(voteOptionJpaRepository.save(voteOptionJpaEntity))
        }
        val voteIdentifier2 =
            VoteIdentifier.of(
                UserFixture.createWithSchoolIdAndGradeAndClassNumber(1, 1, 1),
                LocalDate.now().minusDays(1)
            )
        vote2 =
            votePort.save(Vote.of(voteIdentifier2, voteOptions.map { VoteOptionMapper.mapToDomainEntity(it) }, null))
        val voteIdentifier1 =
            VoteIdentifier.of(UserFixture.createWithSchoolIdAndGradeAndClassNumber(1, 1, 1), LocalDate.now())
        vote1 =
            votePort.save(Vote.of(voteIdentifier1, voteOptions.map { VoteOptionMapper.mapToDomainEntity(it) }, vote2))
    }

    @Test
    fun `본인이 받은 투표 목록을 조회한다`() {
        val loginUser = UserMapper.mapToDomainEntity(users[0])
        UserFixture.setSecurityContextUser(loginUser)
        val now = LocalDateTime.now()
        val plusOneMinute = now.plusMinutes(1)
        ballotJpaRepository.save(
            BallotMapper.mapToJpaEntity(
                Ballot.of(
                    vote1!!.id,
                    vote1!!.voteIdentifier.date,
                    voteOptions[5].id,
                    users[0].id,
                    users[1].id,
                    LocalDateTime.now()
                )
            )
        )
        ballotJpaRepository.save(
            BallotMapper.mapToJpaEntity(
                Ballot.of(
                    vote1!!.id,
                    vote1!!.voteIdentifier.date,
                    voteOptions[5].id,
                    users[1].id,
                    users[0].id,
                    plusOneMinute
                )
            )
        )
        ballotJpaRepository.save(
            BallotMapper.mapToJpaEntity(
                Ballot.of(
                    vote1!!.id,
                    vote1!!.voteIdentifier.date,
                    voteOptions[6].id,
                    users[2].id,
                    users[0].id,
                    plusOneMinute
                )
            )
        )

        // when
        val receivedVotes1 = receivedVoteService.getReceivedVotes(null, 100)
        val receivedVotes2 = receivedVoteService.getReceivedVotes(null, 1)
        val receivedVotes3 = receivedVoteService.getReceivedVotes(receivedVotes2.voteData[0].voteId, 1)

        // then
        receivedVotes1.voteData.size shouldBe 2
        receivedVotes1.voteData[0].date shouldBe plusOneMinute.toLocalDate().toString()
        receivedVotes1.voteData[0].receivedVoteResults.size shouldBe 2
        receivedVotes1.voteData[0].receivedVoteResults[0].voteOption.id shouldBe voteOptions[5].id
        receivedVotes1.voteData[0].receivedVoteResults[0].voteCount shouldBe 1
        receivedVotes1.voteData[0].receivedVoteResults[0].isNew shouldBe true
        receivedVotes1.voteData[0].receivedVoteResults[1].voteOption.id shouldBe voteOptions[6].id
        receivedVotes1.voteData[0].receivedVoteResults[1].voteCount shouldBe 1
        receivedVotes1.voteData[0].receivedVoteResults[1].isNew shouldBe true
        receivedVotes1.voteData[1].date shouldBe now.minusDays(1).toLocalDate().toString()
        receivedVotes1.voteData[1].receivedVoteResults.size shouldBe 0
        receivedVotes2.voteData.size shouldBe 1
        receivedVotes2.voteData[0].voteId shouldBe vote1!!.id
        receivedVotes2.hasNext shouldBe true
        receivedVotes3.voteData.size shouldBe 1
        receivedVotes3.voteData[0].voteId shouldBe vote2!!.id
        receivedVotes3.hasNext shouldBe false
    }

    @Test
    fun `본인이 받지 않은 질문지에 대해 투표를 개별 조회하게 되면 예외가 발생한다`() {
        val now = LocalDateTime.now()
        val plusOneMinute = now.plusMinutes(1)
        val loginUser = UserMapper.mapToDomainEntity(users[0])
        UserFixture.setSecurityContextUser(loginUser)
        ballotJpaRepository.save(
            BallotMapper.mapToJpaEntity(
                Ballot.of(
                    vote1!!.id,
                    vote1!!.voteIdentifier.date,
                    voteOptions[5].id,
                    users[0].id,
                    users[1].id,
                    LocalDateTime.now()
                )
            )
        )
        ballotJpaRepository.save(
            BallotMapper.mapToJpaEntity(
                Ballot.of(
                    vote1!!.id,
                    vote1!!.voteIdentifier.date,
                    voteOptions[5].id,
                    users[1].id,
                    users[0].id,
                    plusOneMinute
                )
            )
        )
        ballotJpaRepository.save(
            BallotMapper.mapToJpaEntity(
                Ballot.of(
                    vote1!!.id,
                    vote1!!.voteIdentifier.date,
                    voteOptions[6].id,
                    users[2].id,
                    users[0].id,
                    plusOneMinute
                )
            )
        )

        // when
        val shouldThrow = shouldThrow<IllegalArgumentException> {
            receivedVoteService.getReceivedVote(
                voteOptions[8].id,
                now.toLocalDate()
            )
        }

        // then
        shouldThrow shouldHaveMessage "해당 유저는 해당 질문지에 대한 투표를 받은 기록이 없습니다."
    }

    @Test
    fun `본인이 받은 투표를 개별 조회한다`() {
        val now = LocalDateTime.now()
        val plusOneMinute = now.plusMinutes(1)
        val loginUser = UserMapper.mapToDomainEntity(users[0])
        UserFixture.setSecurityContextUser(loginUser)
        ballotJpaRepository.save(
            BallotMapper.mapToJpaEntity(
                Ballot.of(
                    vote1!!.id,
                    vote1!!.voteIdentifier.date,
                    voteOptions[5].id,
                    users[0].id,
                    users[1].id,
                    now
                )
            )
        )
        ballotJpaRepository.save(
            BallotMapper.mapToJpaEntity(
                Ballot.of(
                    vote1!!.id,
                    vote1!!.voteIdentifier.date,
                    voteOptions[5].id,
                    users[1].id,
                    users[0].id,
                    plusOneMinute
                )
            )
        )
        ballotJpaRepository.save(
            BallotMapper.mapToJpaEntity(
                Ballot.of(
                    vote1!!.id,
                    vote1!!.voteIdentifier.date,
                    voteOptions[6].id,
                    users[2].id,
                    users[0].id,
                    plusOneMinute
                )
            )
        )

        // when
        val receivedVoteByFirstVoteOption =
            receivedVoteService.getReceivedVote(voteOptions[5].id, now.toLocalDate())
        val receivedVoteBySecondVoteOption =
            receivedVoteService.getReceivedVote(voteOptions[6].id, now.toLocalDate())

        // then
        receivedVoteByFirstVoteOption.voteResult.voteCount shouldBe 1
        receivedVoteByFirstVoteOption.voteResult.user.id shouldBe users[0].id
        receivedVoteByFirstVoteOption.voteResult.rate shouldBe 1
        receivedVoteByFirstVoteOption.voteResult.voteOption.id shouldBe voteOptions[5].id
        receivedVoteBySecondVoteOption.voteResult.voteCount shouldBe 1
        receivedVoteBySecondVoteOption.voteResult.user.id shouldBe users[0].id
        receivedVoteBySecondVoteOption.voteResult.rate shouldBe 1
        receivedVoteBySecondVoteOption.voteResult.voteOption.id shouldBe voteOptions[6].id
    }

    @Test
    fun `본인이 받은 투표를 개별 조회 한 뒤, 읽음 처리 된다`() {
        val now = LocalDateTime.now()
        val plusOneMinute = now.plusMinutes(1)
        val loginUser = UserMapper.mapToDomainEntity(users[0])
        UserFixture.setSecurityContextUser(loginUser)
        ballotJpaRepository.save(
            BallotMapper.mapToJpaEntity(
                Ballot.of(
                    vote1!!.id,
                    vote1!!.voteIdentifier.date,
                    voteOptions[5].id,
                    users[0].id,
                    users[1].id,
                    now
                )
            )
        )
        ballotJpaRepository.save(
            BallotMapper.mapToJpaEntity(
                Ballot.of(
                    vote1!!.id,
                    vote1!!.voteIdentifier.date,
                    voteOptions[5].id,
                    users[1].id,
                    users[0].id,
                    plusOneMinute
                )
            )
        )
        ballotJpaRepository.save(
            BallotMapper.mapToJpaEntity(
                Ballot.of(
                    vote1!!.id,
                    vote1!!.voteIdentifier.date,
                    voteOptions[6].id,
                    users[0].id,
                    users[2].id,
                    plusOneMinute
                )
            )
        )

        // when
        val firstReceivedVotes = receivedVoteService.getReceivedVotes(null, 100)
        receivedVoteService.getReceivedVote(voteOptions[5].id, now.toLocalDate())
        val secondReceivedVotes = receivedVoteService.getReceivedVotes(null, 100)

        // then
        firstReceivedVotes.voteData[0].receivedVoteResults[0].isNew shouldBe true
        secondReceivedVotes.voteData[0].receivedVoteResults[0].isNew shouldBe false
    }

}

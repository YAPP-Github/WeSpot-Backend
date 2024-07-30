package com.wespot.vote.service

import com.wespot.DatabaseCleanup
import com.wespot.user.entity.UserJpaEntity
import com.wespot.user.fixture.UserFixture
import com.wespot.user.mapper.UserMapper
import com.wespot.user.repository.UserJpaRepository
import com.wespot.vote.Ballot
import com.wespot.vote.BallotJpaRepository
import com.wespot.vote.BallotMapper
import com.wespot.vote.Vote
import com.wespot.vote.VoteIdentifier
import com.wespot.vote.VoteJpaEntity
import com.wespot.vote.VoteJpaRepository
import com.wespot.vote.VoteMapper
import com.wespot.vote.fixture.VoteFixture
import com.wespot.vote.port.out.VotePort
import com.wespot.voteoption.VoteOptionJpaEntity
import com.wespot.voteoption.VoteOptionJpaRepository
import com.wespot.voteoption.VoteOptionMapper
import com.wespot.voteoption.fixture.VoteOptionFixture
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@SpringBootTest
class SentVoteServiceTest @Autowired constructor(
    private var sentVoteService: SentVoteService,
    private var databaseCleanup: DatabaseCleanup,
    private var userJpaRepository: UserJpaRepository,
    private var voteOptionJpaRepository: VoteOptionJpaRepository,
    private var ballotJpaRepository: BallotJpaRepository,
    private var votePort: VotePort,
) {

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

    @AfterEach
    fun tearDown() {
        databaseCleanup.execute()
    }

    @Test
    fun `본인이 보낸 투표 목록을 조회한다`() {
        // given
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
                    voteOptions[7].id,
                    users[0].id,
                    users[2].id,
                    plusOneMinute
                )
            )
        )

        // when
        val sentVotes = sentVoteService.getSentVotes()

        // then
        sentVotes.voteData.size shouldBe 2
        sentVotes.voteData[0].date shouldBe now.toLocalDate().toString()
        sentVotes.voteData[0].sentVoteResults.size shouldBe 2
        sentVotes.voteData[0].sentVoteResults[0].voteCount shouldBe 1
        sentVotes.voteData[0].sentVoteResults[1].voteCount shouldBe 1
        sentVotes.voteData[1].date shouldBe now.minusDays(1).toLocalDate().toString()
        sentVotes.voteData[1].sentVoteResults.size shouldBe 0
    }

    @Test
    fun `본인이 보낸 투표를 개별 조회한다`() {
        // given
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
        val sentVoteByFirstVoteOption = sentVoteService.getSentVote(voteOptions[5].id, now.toLocalDate())
        val sentVoteBySecondVoteOption = sentVoteService.getSentVote(voteOptions[6].id, now.toLocalDate())

        // then
        sentVoteByFirstVoteOption.voteResult.voteUsers.size shouldBe 1
        sentVoteByFirstVoteOption.voteResult.voteOption.id shouldBe voteOptions[5].id
        sentVoteBySecondVoteOption.voteResult.voteUsers.size shouldBe 1
        sentVoteBySecondVoteOption.voteResult.voteOption.id shouldBe voteOptions[6].id
    }

}

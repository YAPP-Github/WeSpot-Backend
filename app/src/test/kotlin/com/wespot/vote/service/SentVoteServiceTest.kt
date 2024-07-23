package com.wespot.vote.service

import com.wespot.DatabaseCleanup
import com.wespot.user.entity.UserJpaEntity
import com.wespot.user.fixture.UserFixture
import com.wespot.user.mapper.UserMapper
import com.wespot.user.repository.UserJpaRepository
import com.wespot.vote.Ballot
import com.wespot.vote.BallotJpaRepository
import com.wespot.vote.BallotMapper
import com.wespot.vote.VoteJpaEntity
import com.wespot.vote.VoteJpaRepository
import com.wespot.vote.VoteMapper
import com.wespot.vote.fixture.VoteFixture
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
    private var voteJpaRepository: VoteJpaRepository,
    private var ballotJpaRepository: BallotJpaRepository,
) {

    private var users: MutableList<UserJpaEntity> = mutableListOf()
    private var voteOptions: MutableList<VoteOptionJpaEntity> = mutableListOf()
    private var savedVote: VoteJpaEntity? = null

    @BeforeEach
    fun setUp() {
        voteOptions.clear()
        users.clear()
        for (i in 0 until 8) {
            val userJpaEntity = UserMapper.mapToJpaEntity(UserFixture.createWithIdAndEmail(0, "TestEmail${i}@Kakako"))
            users.add(userJpaRepository.save(userJpaEntity))
            val voteOptionJpaEntity =
                VoteOptionMapper.mapToJpaEntity(VoteOptionFixture.createWithId(0))
            voteOptions.add(voteOptionJpaRepository.save(voteOptionJpaEntity))
        }
        savedVote = voteJpaRepository.save(
            VoteMapper.mapToJpaEntity(
                VoteFixture.createWithIdAndVoteNumberAndBallotsAndCreatedAt(
                    0,
                    0,
                    Collections.emptyList(),
                    LocalDate.now()
                )
            )
        )
        voteJpaRepository.save(
            VoteMapper.mapToJpaEntity(
                VoteFixture.createWithIdAndVoteNumberAndBallotsAndCreatedAt(
                    0,
                    0,
                    Collections.emptyList(),
                    LocalDate.now().minusDays(1)
                )
            )
        )
    }

    @AfterEach
    fun tearDown() {
        databaseCleanup.execute()
    }

    @Test
    fun `본인이 보낸 투표 목록을 조회한다`() {
        // given
        val now = LocalDateTime.now()
        mockkStatic(LocalDateTime::class)
        every { LocalDateTime.now() } returns now
        val loginUser = UserMapper.mapToDomainEntity(users[0])
        UserFixture.setSecurityContextUser(loginUser)
        ballotJpaRepository.save(
            BallotMapper.mapToJpaEntity(
                Ballot.of(
                    savedVote!!.id,
                    voteOptions[0].id,
                    users[0].id,
                    users[1].id
                )
            )
        )
        every { LocalDateTime.now() } returns now.plusMinutes(1)
        ballotJpaRepository.save(
            BallotMapper.mapToJpaEntity(
                Ballot.of(
                    savedVote!!.id,
                    voteOptions[0].id,
                    users[1].id,
                    users[0].id
                )
            )
        )
        ballotJpaRepository.save(
            BallotMapper.mapToJpaEntity(
                Ballot.of(
                    savedVote!!.id,
                    voteOptions[2].id,
                    users[0].id,
                    users[2].id
                )
            )
        )

        // when
        val sentVotes = sentVoteService.getSentVotes()

        // then
        sentVotes.voteData.size shouldBe 2
        sentVotes.voteData[0].date shouldBe LocalDate.now().toString()
        sentVotes.voteData[0].sentVoteResults.size shouldBe 2
        sentVotes.voteData[0].sentVoteResults[0].voteCount shouldBe 1
        sentVotes.voteData[0].sentVoteResults[1].voteCount shouldBe 1
        sentVotes.voteData[1].date shouldBe LocalDate.now().minusDays(1).toString()
        sentVotes.voteData[1].sentVoteResults.size shouldBe 0
    }

    @Test
    fun `본인이 보낸 투표를 개별 조회한다`() {
        // given
        val now = LocalDateTime.now()
        mockkStatic(LocalDateTime::class)
        every { LocalDateTime.now() } returns now
        val loginUser = UserMapper.mapToDomainEntity(users[0])
        UserFixture.setSecurityContextUser(loginUser)
        ballotJpaRepository.save(
            BallotMapper.mapToJpaEntity(
                Ballot.of(
                    savedVote!!.id,
                    voteOptions[0].id,
                    users[0].id,
                    users[1].id
                )
            )
        )
        every { LocalDateTime.now() } returns now.plusMinutes(1)
        ballotJpaRepository.save(
            BallotMapper.mapToJpaEntity(
                Ballot.of(
                    savedVote!!.id,
                    voteOptions[0].id,
                    users[1].id,
                    users[0].id
                )
            )
        )
        ballotJpaRepository.save(
            BallotMapper.mapToJpaEntity(
                Ballot.of(
                    savedVote!!.id,
                    voteOptions[1].id,
                    users[0].id,
                    users[2].id
                )
            )
        )

        // when
        val sentVoteByFirstVoteOption = sentVoteService.getSentVote(voteOptions[0].id, now.toLocalDate())
        val sentVoteBySecondVoteOption = sentVoteService.getSentVote(voteOptions[1].id, now.toLocalDate())

        // then
        sentVoteByFirstVoteOption.voteResult.voteUsers.size shouldBe 1
        sentVoteByFirstVoteOption.voteResult.voteOption.id shouldBe voteOptions[0].id
        sentVoteBySecondVoteOption.voteResult.voteUsers.size shouldBe 1
        sentVoteBySecondVoteOption.voteResult.voteOption.id shouldBe voteOptions[1].id
    }

}

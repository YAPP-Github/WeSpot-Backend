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
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
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
class ReceivedVoteServiceTest @Autowired constructor(
    private var receivedVoteService: ReceivedVoteService,
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
                VoteFixture.createWithIdAndVoteNumberAndBallots(0, 0, Collections.emptyList())
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
    fun `본인이 받은 투표 목록을 조회한다`() {
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
                    users[2].id,
                    users[0].id
                )
            )
        )

        // when
        val receivedVotes = receivedVoteService.getReceivedVotes()

        // then
        receivedVotes.voteData.size shouldBe 2
        receivedVotes.voteData[0].date shouldBe LocalDate.now().toString()
        receivedVotes.voteData[0].receivedVoteResults.size shouldBe 2
        receivedVotes.voteData[0].receivedVoteResults[0].voteOption.id shouldBe voteOptions[0].id
        receivedVotes.voteData[0].receivedVoteResults[0].voteCount shouldBe 1
        receivedVotes.voteData[0].receivedVoteResults[0].isNew shouldBe true
        receivedVotes.voteData[0].receivedVoteResults[1].voteOption.id shouldBe voteOptions[1].id
        receivedVotes.voteData[0].receivedVoteResults[1].voteCount shouldBe 1
        receivedVotes.voteData[0].receivedVoteResults[1].isNew shouldBe true
        receivedVotes.voteData[1].date shouldBe LocalDate.now().minusDays(1).toString()
        receivedVotes.voteData[1].receivedVoteResults.size shouldBe 0
    }

    @Test
    fun `본인이 받지 않은 질문지에 대해 투표를 개별 조회하게 되면 예외가 발생한다`() {
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
                    users[2].id,
                    users[0].id
                )
            )
        )

        // when
        val shouldThrow = shouldThrow<IllegalArgumentException> { receivedVoteService.getReceivedVote(voteOptions[3].id, now.toLocalDate()) }

        // then
        shouldThrow shouldHaveMessage "해당 유저는 해당 질문지에 대한 투표를 받은 기록이 없습니다."
    }

    @Test
    fun `본인이 받은 투표를 개별 조회한다`() {
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
                    users[2].id,
                    users[0].id
                )
            )
        )

        // when
        val receivedVoteByFirstVoteOption = receivedVoteService.getReceivedVote(voteOptions[0].id, now.toLocalDate())
        val receivedVoteBySecondVoteOption = receivedVoteService.getReceivedVote(voteOptions[1].id, now.toLocalDate())

        // then
        receivedVoteByFirstVoteOption.voteResult.voteCount shouldBe 1
        receivedVoteByFirstVoteOption.voteResult.user.id shouldBe users[0].id
        receivedVoteByFirstVoteOption.voteResult.rate shouldBe 1
        receivedVoteByFirstVoteOption.voteResult.voteOption.id shouldBe voteOptions[0].id
        receivedVoteBySecondVoteOption.voteResult.voteCount shouldBe 1
        receivedVoteBySecondVoteOption.voteResult.user.id shouldBe users[0].id
        receivedVoteBySecondVoteOption.voteResult.rate shouldBe 1
        receivedVoteBySecondVoteOption.voteResult.voteOption.id shouldBe voteOptions[1].id
    }

    @Test
    fun `본인이 받은 투표를 개별 조회 한 뒤, 읽음 처리 된다`() {
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
        val firstReceivedVotes = receivedVoteService.getReceivedVotes()
        receivedVoteService.getReceivedVote(voteOptions[0].id, now.toLocalDate())
        val secondReceivedVotes = receivedVoteService.getReceivedVotes()

        // then
        firstReceivedVotes.voteData[0].receivedVoteResults[0].isNew shouldBe true
        secondReceivedVotes.voteData[0].receivedVoteResults[0].isNew shouldBe false
    }

}

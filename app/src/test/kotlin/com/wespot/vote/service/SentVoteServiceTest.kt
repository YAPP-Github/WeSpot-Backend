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
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.time.LocalDateTime

@SpringBootTest
class SentVoteServiceTest @Autowired constructor(
    private var sentVoteService: SentVoteService,
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
        sentVotes.voteData[0].sentVoteResults[0].vote.voteOption.id shouldBe voteOptions[5].id
        sentVotes.voteData[0].sentVoteResults[1].vote.voteOption.id shouldBe voteOptions[7].id
        sentVotes.voteData[0].sentVoteResults[0].vote.user.id shouldBe users[1].id
        sentVotes.voteData[0].sentVoteResults[1].vote.user.id shouldBe users[2].id
        sentVotes.voteData[1].date shouldBe now.minusDays(1).toLocalDate().toString()
        sentVotes.voteData[1].sentVoteResults.size shouldBe 0
    }

}

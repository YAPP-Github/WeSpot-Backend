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
class VoteRankServiceTest @Autowired constructor(
    private var voteRankService: VoteRankService,
    private var userJpaRepository: UserJpaRepository,
    private var voteOptionJpaRepository: VoteOptionJpaRepository,
    private var ballotJpaRepository: BallotJpaRepository,
    private var votePort: VotePort
) : ServiceTest() {

    private var users: MutableList<UserJpaEntity> = mutableListOf()
    private var voteOptions: MutableList<VoteOptionJpaEntity> = mutableListOf()
    private var vote: Vote? = null

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
        val voteIdentifier =
            VoteIdentifier.of(
                UserFixture.createWithSchoolIdAndGradeAndClassNumber(1, 1, 1),
                LocalDate.now()
            )
        vote =
            votePort.save(Vote.of(voteIdentifier, voteOptions.map { VoteOptionMapper.mapToDomainEntity(it) }, null))
    }

    @Test
    fun `투표 결과 1~5등을 조회한다`() {
        // given
        val now = LocalDateTime.now()
        val loginUser = UserMapper.mapToDomainEntity(users[0])
        UserFixture.setSecurityContextUser(loginUser)
        ballotJpaRepository.save(
            BallotMapper.mapToJpaEntity(
                Ballot.of(
                    vote!!.id,
                    vote!!.voteIdentifier.date,
                    voteOptions[0].id,
                    users[0].id,
                    users[1].id,
                    now
                )
            )
        )
        Thread.sleep(1)
        ballotJpaRepository.save(
            BallotMapper.mapToJpaEntity(
                Ballot.of(
                    vote!!.id,
                    vote!!.voteIdentifier.date,
                    voteOptions[0].id,
                    users[1].id,
                    users[0].id,
                    now
                )
            )
        )
        ballotJpaRepository.save(
            BallotMapper.mapToJpaEntity(
                Ballot.of(
                    vote!!.id,
                    vote!!.voteIdentifier.date,
                    voteOptions[1].id,
                    users[0].id,
                    users[2].id,
                    now
                )
            )
        )

        // when
        val voteResultsOfTop5 = voteRankService.getVoteResultsOfTop5(LocalDate.now())

        // then
        voteResultsOfTop5.voteResults.size shouldBe 5
        voteResultsOfTop5.voteResults[0].voteResults.size shouldBe 2
        voteResultsOfTop5.voteResults[0].voteResults[0].voteCount shouldBe 1
        voteResultsOfTop5.voteResults[0].voteResults[0].user.id shouldBe users[1].id
        voteResultsOfTop5.voteResults[0].voteResults[1].voteCount shouldBe 1
        voteResultsOfTop5.voteResults[0].voteResults[1].user.id shouldBe users[0].id
        voteResultsOfTop5.voteResults[1].voteResults.size shouldBe 1
        voteResultsOfTop5.voteResults[1].voteResults[0].voteCount shouldBe 1
        voteResultsOfTop5.voteResults[1].voteResults[0].user.id shouldBe users[2].id
    }

    @Test
    fun `투표 결과 1등을 조회한다`() {
        // given
        val now = LocalDateTime.now()
        val loginUser = UserMapper.mapToDomainEntity(users[0])
        UserFixture.setSecurityContextUser(loginUser)
        ballotJpaRepository.save(
            BallotMapper.mapToJpaEntity(
                Ballot.of(
                    vote!!.id,
                    vote!!.voteIdentifier.date,
                    voteOptions[0].id,
                    users[0].id,
                    users[1].id,
                    now
                )
            )
        )
        Thread.sleep(1)
        ballotJpaRepository.save(
            BallotMapper.mapToJpaEntity(
                Ballot.of(
                    vote!!.id,
                    vote!!.voteIdentifier.date,
                    voteOptions[0].id,
                    users[1].id,
                    users[0].id,
                    now
                )
            )
        )
        ballotJpaRepository.save(
            BallotMapper.mapToJpaEntity(
                Ballot.of(
                    vote!!.id,
                    vote!!.voteIdentifier.date,
                    voteOptions[1].id,
                    users[0].id,
                    users[2].id,
                    now
                )
            )
        )

        // when
        val voteResultsOfTop1 = voteRankService.getVoteResultsOfTop1(now.toLocalDate())

        // then
        voteResultsOfTop1.voteResults.size shouldBe 5
        voteResultsOfTop1.voteResults[0].results[0].user.id shouldBe users[1].id
        voteResultsOfTop1.voteResults[1].results[0].user.id shouldBe users[2].id
        voteResultsOfTop1.voteResults[2].results.size shouldBe 0
        voteResultsOfTop1.voteResults[3].results.size shouldBe 0
        voteResultsOfTop1.voteResults[4].results.size shouldBe 0
    }

}

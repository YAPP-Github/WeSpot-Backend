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
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.util.*

@SpringBootTest
class VoteRankServiceTest @Autowired constructor(
    private var voteRankService: VoteRankService,
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
        val vote =
            VoteFixture.createWithIdAndVoteNumberAndBallots(0, 0, Collections.emptyList())
        savedVote = voteJpaRepository.save(VoteMapper.mapToJpaEntity(vote))
    }

    @AfterEach
    fun tearDown() {
        databaseCleanup.execute()
    }

    @Test
    fun `투표 결과 1~5등을 조회한다`() {
        // given
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
        Thread.sleep(1)
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
        Thread.sleep(1)
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
        val voteResultsOfTop1 = voteRankService.getVoteResultsOfTop1(LocalDate.now())

        // then
        voteResultsOfTop1.voteResults.size shouldBe 5
        voteResultsOfTop1.voteResults[0].voteResult!!.user.id shouldBe users[1].id
        voteResultsOfTop1.voteResults[1].voteResult!!.user.id shouldBe users[2].id
        voteResultsOfTop1.voteResults[2].voteResult shouldBe null
        voteResultsOfTop1.voteResults[3].voteResult shouldBe null
        voteResultsOfTop1.voteResults[4].voteResult shouldBe null
    }

}

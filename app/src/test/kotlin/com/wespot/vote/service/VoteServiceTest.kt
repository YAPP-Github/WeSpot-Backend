package com.wespot.vote.service

import com.wespot.DatabaseCleanup
import com.wespot.user.entity.UserJpaEntity
import com.wespot.user.fixture.UserFixture
import com.wespot.user.mapper.UserMapper
import com.wespot.user.repository.UserJpaRepository
import com.wespot.vote.BallotJpaRepository
import com.wespot.vote.VoteJpaRepository
import com.wespot.vote.VoteMapper
import com.wespot.vote.dto.request.VoteRequest
import com.wespot.vote.dto.request.VoteRequests
import com.wespot.vote.fixture.VoteFixture
import com.wespot.voteoption.VoteOptionJpaEntity
import com.wespot.voteoption.VoteOptionJpaRepository
import com.wespot.voteoption.VoteOptionMapper
import com.wespot.voteoption.fixture.VoteOptionFixture
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import kotlin.test.Test

@SpringBootTest
class VoteServiceTest(
    @Autowired
    private var voteService: SaveVoteService,
    @Autowired
    private var databaseCleanup: DatabaseCleanup,
    @Autowired
    private var userJpaRepository: UserJpaRepository,
    @Autowired
    private var voteOptionJpaRepository: VoteOptionJpaRepository,
    @Autowired
    private var voteJpaRepository: VoteJpaRepository,
    @Autowired
    private var ballotJpaRepository: BallotJpaRepository,
) {

    private var users: MutableList<UserJpaEntity> = mutableListOf()
    private var voteOptions: MutableList<VoteOptionJpaEntity> = mutableListOf()

    @BeforeEach
    fun setUp() {
        voteOptions.clear()
        users.clear()
        for (i in 0 until 8) {
            val userJpaEntity = UserMapper.mapToJpaEntity(UserFixture.createWithId(0))
            users.add(userJpaRepository.save(userJpaEntity))
            val voteOptionJpaEntity =
                VoteOptionMapper.mapToJpaEntity(VoteOptionFixture.createWithId(0))
            voteOptions.add(voteOptionJpaRepository.save(voteOptionJpaEntity))
        }
        val vote =
            VoteFixture.createWithIdAndVoteNumberAndBallots(0, 0, Collections.emptyList())
        voteJpaRepository.save(VoteMapper.mapToJpaEntity(vote))
    }

    @AfterEach
    fun tearDown() {
        databaseCleanup.execute()
    }

    @Test
    fun `투표에 지정된 질문지를 반환받는다`() {
        // given when
        val voteOptions = voteService.getVoteOptions(users[0].id)

        // then
        voteOptions.voteItems.size shouldBe 5
        voteOptions.voteItems[0].voteOptions.size shouldBe 5
    }

    @Test
    fun `투표시 존재하지 않는 사용자가 포함되어 있으면 예외가 발생한다`() {
        // given
        val requests = VoteRequests(
            listOf(
                VoteRequest(
                    userId = Long.MAX_VALUE,
                    voteOptionId = voteOptions[0].id
                ),
            )
        )

        // when
        val throwingCallable = { voteService.saveVote(users[users.size - 1].id, requests) }

        // then
        val shouldThrow = shouldThrow<IllegalArgumentException>(throwingCallable)
        shouldThrow shouldHaveMessage "투표하고자 하는 회원이 존재하지 않습니다."
    }

    @Test
    fun `한번에 5명을 초과해 투표를 진행할 경우 예외가 발생한다`() {
        // given
        val requests = VoteRequests(
            listOf(
                VoteRequest(
                    userId = users[0].id,
                    voteOptionId = voteOptions[0].id
                ),
                VoteRequest(
                    userId = users[1].id,
                    voteOptionId = voteOptions[0].id
                ),
                VoteRequest(
                    userId = users[2].id,
                    voteOptionId = voteOptions[0].id
                ),
                VoteRequest(
                    userId = users[3].id,
                    voteOptionId = voteOptions[0].id,
                ),
                VoteRequest(
                    userId = users[4].id,
                    voteOptionId = voteOptions[0].id
                ),
                VoteRequest(
                    userId = users[5].id,
                    voteOptionId = voteOptions[0].id
                ),
            )
        )

        // when
        val throwingCallable = { voteService.saveVote(users[users.size - 1].id, requests) }

        // then
        val shouldThrow = shouldThrow<IllegalArgumentException>(throwingCallable)
        shouldThrow shouldHaveMessage "투표는 한번에 최대 5명에게 할 수 있습니다."
    }

    @Test
    fun `투표시 존재하지 않는 질문지가 포함되어 있다면 예외가 발생한다`() {
        // given
        val requests = VoteRequests(
            listOf(
                VoteRequest(
                    userId = users[0].id,
                    voteOptionId = Long.MAX_VALUE
                ),
            )
        )

        // when
        val throwingCallable = { voteService.saveVote(users[users.size - 1].id, requests) }

        // then
        val shouldThrow = shouldThrow<IllegalArgumentException>(throwingCallable)
        shouldThrow shouldHaveMessage "오늘 제공된 질문지만 선택해 투표할 수 있습니다."
    }

    @Test
    fun `투표를 정상적으로 진행한다`() {
        // given
        val requests = VoteRequests(
            listOf(
                VoteRequest(
                    userId = users[0].id,
                    voteOptionId = voteOptions[0].id
                ),
                VoteRequest(
                    userId = users[1].id,
                    voteOptionId = voteOptions[0].id
                ),
                VoteRequest(
                    userId = users[2].id,
                    voteOptionId = voteOptions[0].id
                ),
                VoteRequest(
                    userId = users[3].id,
                    voteOptionId = voteOptions[0].id
                ),
                VoteRequest(
                    userId = users[4].id,
                    voteOptionId = voteOptions[0].id
                ),
            )
        )

        // when
        voteService.saveVote(users[users.size - 1].id, requests)

        // then
        val ballots = ballotJpaRepository.findAll()
        val votedUserIds = requests.voteRequests.stream()
            .map { it.userId }
            .toList()
        ballots.size shouldBe 5
        ballots[0].senderId shouldBe users[users.size - 1].id
        ballots[1].senderId shouldBe users[users.size - 1].id
        ballots[2].senderId shouldBe users[users.size - 1].id
        ballots[3].senderId shouldBe users[users.size - 1].id
        ballots[4].senderId shouldBe users[users.size - 1].id
        ballots[0].receiverId shouldBeIn votedUserIds
        ballots[1].receiverId shouldBeIn votedUserIds
        ballots[2].receiverId shouldBeIn votedUserIds
        ballots[3].receiverId shouldBeIn votedUserIds
        ballots[4].receiverId shouldBeIn votedUserIds
    }

}

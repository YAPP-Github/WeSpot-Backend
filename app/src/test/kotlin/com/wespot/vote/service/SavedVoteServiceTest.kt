package com.wespot.vote.service

import com.wespot.DatabaseCleanup
import com.wespot.common.service.ServiceTest
import com.wespot.exception.CustomException
import com.wespot.notification.port.out.NotificationPort
import com.wespot.user.entity.UserJpaEntity
import com.wespot.user.fixture.UserFixture
import com.wespot.user.mapper.UserMapper
import com.wespot.user.repository.UserJpaRepository
import com.wespot.vote.BallotJpaRepository
import com.wespot.vote.Vote
import com.wespot.vote.VoteIdentifier
import com.wespot.vote.dto.request.VoteRequest
import com.wespot.vote.dto.request.VoteRequests
import com.wespot.vote.port.out.VotePort
import com.wespot.voteoption.VoteOptionJpaEntity
import com.wespot.voteoption.VoteOptionJpaRepository
import com.wespot.voteoption.VoteOptionMapper
import com.wespot.voteoption.fixture.VoteOptionFixture
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate
import kotlin.test.Test

class SavedVoteServiceTest @Autowired constructor(
    private val voteService: SavedVoteService,
    private val userJpaRepository: UserJpaRepository,
    private val voteOptionJpaRepository: VoteOptionJpaRepository,
    private val ballotJpaRepository: BallotJpaRepository,
    private val votePort: VotePort,
    private val notificationPort: NotificationPort,
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
    fun `투표에 지정된 질문지를 반환받는다`() {
        // given
        val loginUser = UserMapper.mapToDomainEntity(users[users.size - 1])
        UserFixture.setSecurityContextUser(loginUser)

        // when
        val voteOptions = voteService.getVoteOptions()

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
        val loginUser = UserMapper.mapToDomainEntity(users[users.size - 1])
        UserFixture.setSecurityContextUser(loginUser)
        val throwingCallable = { voteService.saveVote(requests) }

        // then
        val shouldThrow = shouldThrow<CustomException>(throwingCallable)
        shouldThrow shouldHaveMessage "투표 대상을 찾을 수 없습니다."
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
        val loginUser = UserMapper.mapToDomainEntity(users[users.size - 1])
        UserFixture.setSecurityContextUser(loginUser)
        val throwingCallable = { voteService.saveVote(requests) }

        // then
        val shouldThrow = shouldThrow<CustomException>(throwingCallable)
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
        val loginUser = UserMapper.mapToDomainEntity(users[users.size - 1])
        UserFixture.setSecurityContextUser(loginUser)
        val throwingCallable = { voteService.saveVote(requests) }

        // then
        val shouldThrow = shouldThrow<CustomException>(throwingCallable)
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
        val loginUser = UserMapper.mapToDomainEntity(users[users.size - 1])
        UserFixture.setSecurityContextUser(loginUser)
        voteService.saveVote(requests)
        val notifications = notificationPort.findAll()

        // then
        val ballots = ballotJpaRepository.findAll()
        val votedUserIds = requests.votes.stream()
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
        notifications.size shouldBe 5
    }

    @Test
    fun `투표를 5명이 보내면 알림이 발생한다`() {
        // given
        val requests1 = VoteRequests(
            listOf(
                VoteRequest(
                    userId = users[0].id,
                    voteOptionId = voteOptions[0].id
                )
            )
        )
        val requests2 = VoteRequests(
            listOf(
                VoteRequest(
                    userId = users[1].id,
                    voteOptionId = voteOptions[0].id
                )
            )
        )
        val requests3 = VoteRequests(
            listOf(
                VoteRequest(
                    userId = users[2].id,
                    voteOptionId = voteOptions[0].id
                )
            )
        )
        val requests4 = VoteRequests(
            listOf(
                VoteRequest(
                    userId = users[3].id,
                    voteOptionId = voteOptions[0].id
                )
            )
        )
        val requests5 = VoteRequests(
            listOf(
                VoteRequest(
                    userId = users[4].id,
                    voteOptionId = voteOptions[0].id
                )
            )
        )

        // when
        var loginUser = UserMapper.mapToDomainEntity(users[users.size - 1])
        UserFixture.setSecurityContextUser(loginUser)
        voteService.saveVote(requests1)
        loginUser = UserMapper.mapToDomainEntity(users[users.size - 2])
        UserFixture.setSecurityContextUser(loginUser)
        voteService.saveVote(requests2)
        loginUser = UserMapper.mapToDomainEntity(users[users.size - 3])
        UserFixture.setSecurityContextUser(loginUser)
        voteService.saveVote(requests3)
        loginUser = UserMapper.mapToDomainEntity(users[users.size - 6])
        UserFixture.setSecurityContextUser(loginUser)
        voteService.saveVote(requests4)
        loginUser = UserMapper.mapToDomainEntity(users[users.size - 7])
        UserFixture.setSecurityContextUser(loginUser)
        voteService.saveVote(requests5)
        val notifications = notificationPort.findAll()

        // then
        notifications.size shouldBe 12
        notifications[5].title shouldBe "우리 반 투표 결과가 업데이트 되었어요 \uD83D\uDC40"
        notifications[5].body shouldBe "실시간 1등은 누구일까요? 눌러서 바로 확인해 보세요"
    }

}

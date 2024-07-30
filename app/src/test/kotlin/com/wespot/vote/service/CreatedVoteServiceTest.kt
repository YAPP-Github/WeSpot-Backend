package com.wespot.vote.service

import com.wespot.DatabaseCleanup
import com.wespot.user.fixture.UserFixture
import com.wespot.user.mapper.UserMapper
import com.wespot.user.repository.UserJpaRepository
import com.wespot.vote.Vote
import com.wespot.vote.VoteIdentifier
import com.wespot.vote.VoteJpaRepository
import com.wespot.vote.VoteMapper
import com.wespot.vote.port.out.VoteOptionPort
import com.wespot.vote.port.out.VotePort
import com.wespot.voteoption.VoteOption
import com.wespot.voteoption.VoteOptionJpaRepository
import com.wespot.voteoption.VoteOptionMapper
import com.wespot.voteoption.fixture.VoteOptionFixture
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.throwable.shouldHaveMessage
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
class CreatedVoteServiceTest @Autowired constructor(
    private var createdVoteService: CreatedVoteService,
    private var userJpaRepository: UserJpaRepository,
    private var voteJpaRepository: VoteJpaRepository,
    private var voteOptionJpaRepository: VoteOptionJpaRepository,
    private var voteOptionPort: VoteOptionPort,
    private var votePort: VotePort,
    private var databaseCleanup: DatabaseCleanup
) {

    private var voteOptions: List<VoteOption> = mutableListOf()

    private var users = listOf(
        UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber("Test0@Kakao", 1, 1, 1),
        UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber("Test1@Kakao", 1, 1, 1),
        UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber("Test2@Kakao", 1, 1, 1),
        UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber("Test3@Kakao", 1, 1, 1),
        UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber("Test4@Kakao", 1, 1, 2),
        UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber("Test5@Kakao", 1, 1, 2),
        UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber("Test6@Kakao", 1, 1, 2),
        UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber("Test7@Kakao", 1, 1, 3),
        UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber("Test8@Kakao", 1, 1, 3),
        UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber("Test9@Kakao", 1, 1, 4),
        UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber("Test10@Kakao", 1, 1, 5),
        UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber("Test11@Kakao", 1, 1, 6),
    )

    @BeforeEach
    fun setUp() {
        voteOptions = listOf(
            voteOptionJpaRepository.save(VoteOptionMapper.mapToJpaEntity(VoteOptionFixture.create())),
            voteOptionJpaRepository.save(VoteOptionMapper.mapToJpaEntity(VoteOptionFixture.create())),
            voteOptionJpaRepository.save(VoteOptionMapper.mapToJpaEntity(VoteOptionFixture.create())),
            voteOptionJpaRepository.save(VoteOptionMapper.mapToJpaEntity(VoteOptionFixture.create())),
            voteOptionJpaRepository.save(VoteOptionMapper.mapToJpaEntity(VoteOptionFixture.create())),
            voteOptionJpaRepository.save(VoteOptionMapper.mapToJpaEntity(VoteOptionFixture.create())),
            voteOptionJpaRepository.save(VoteOptionMapper.mapToJpaEntity(VoteOptionFixture.create())),
            voteOptionJpaRepository.save(VoteOptionMapper.mapToJpaEntity(VoteOptionFixture.create())),
            voteOptionJpaRepository.save(VoteOptionMapper.mapToJpaEntity(VoteOptionFixture.create())),
            voteOptionJpaRepository.save(VoteOptionMapper.mapToJpaEntity(VoteOptionFixture.create()))
        ).map { VoteOptionMapper.mapToDomainEntity(it) }
    }

    @AfterEach
    fun tearDown() {
        databaseCleanup.execute()
    }

    @Test
    fun `학급당 하루에 하나만의 투표를 만든다`() {
        // given
        userJpaRepository.saveAll(
            listOf(
                UserMapper.mapToJpaEntity(users[0]),
                UserMapper.mapToJpaEntity(users[1]),
                UserMapper.mapToJpaEntity(users[2]),
                UserMapper.mapToJpaEntity(users[3]),
                UserMapper.mapToJpaEntity(users[4]),
                UserMapper.mapToJpaEntity(users[5]),
                UserMapper.mapToJpaEntity(users[6]),
                UserMapper.mapToJpaEntity(users[7]),
                UserMapper.mapToJpaEntity(users[8]),
                UserMapper.mapToJpaEntity(users[9]),
                UserMapper.mapToJpaEntity(users[10]),
                UserMapper.mapToJpaEntity(users[11]),
            )
        )

        // when
        createdVoteService.createVotes()
        val votes = voteJpaRepository.findAll()
            .sortedBy { it.classNumber }

        println(votes.size)
        println(votes[0].classNumber)
        println(votes[0].voteNumber)
        println(votes[1].classNumber)
        println(votes[1].voteNumber)
        println(votes[2].classNumber)
        println(votes[2].voteNumber)
        println(votes[3].classNumber)
        println(votes[3].voteNumber)
        println(votes[4].classNumber)
        println(votes[4].voteNumber)
        println(votes[5].classNumber)
        println(votes[5].voteNumber)

        // then
        votes.size shouldBe 6
        votes[0].classNumber shouldBe 1
        votes[0].voteNumber shouldBe 0
        votes[1].classNumber shouldBe 2
        votes[1].voteNumber shouldBe 0
        votes[2].classNumber shouldBe 3
        votes[2].voteNumber shouldBe 0
        votes[3].classNumber shouldBe 4
        votes[3].voteNumber shouldBe 0
        votes[4].classNumber shouldBe 5
        votes[4].voteNumber shouldBe 0
        votes[5].classNumber shouldBe 6
        votes[5].voteNumber shouldBe 0
    }

    @Test
    fun `학급에 이미 투표가 존재하면 새로 생성하지 않는다`() {
        // given
        userJpaRepository.save(UserMapper.mapToJpaEntity(users[0]))
        val voteIdentifier = VoteIdentifier.of(users[0], LocalDate.now())
        val vote = Vote.of(voteIdentifier, voteOptionPort.findAll(), null)
        val savedVote = voteJpaRepository.save(VoteMapper.mapToJpaEntity(vote))

        // when
        createdVoteService.createVotes()
        val votes = voteJpaRepository.findAll()

        // then
        votes.size shouldBe 1
        votes[0].id shouldBe savedVote.id
        votes[0].schoolId shouldBe 1
        votes[0].grade shouldBe 1
        votes[0].classNumber shouldBe 1
        votes[0].voteNumber shouldBe 0
    }

    @Test
    fun `처음으로 생기는 투표가 아니라면, 이전 날의 투표의 VoteNumber에서 1만큼 높게 설정된다`() {
        userJpaRepository.save(UserMapper.mapToJpaEntity(users[0]))
        val voteIdentifier = VoteIdentifier.of(users[0], LocalDate.now().minusDays(1))
        val vote = Vote.of(voteIdentifier, voteOptionPort.findAll(), null)
        val savedVote = voteJpaRepository.save(VoteMapper.mapToJpaEntity(vote))

        // when
        createdVoteService.createVotes()
        val votes = voteJpaRepository.findAll().sortedBy { it.id }

        // then
        votes.size shouldBe 2
        votes[0].id shouldBe savedVote.id
        votes[1].schoolId shouldBe 1
        votes[1].grade shouldBe 1
        votes[1].classNumber shouldBe 1
        votes[1].voteNumber shouldBe 1
    }

    @Test
    fun `학급에 처음으로 가입한 회원이 존재하는 경우 투표를 생성한다`() {
        // given
        val savedUserJpaEntity = userJpaRepository.save(UserMapper.mapToJpaEntity(users[0]))
        val savedUserDomainEntity = UserMapper.mapToDomainEntity(savedUserJpaEntity)

        // when
        createdVoteService.createVoteByUser(savedUserDomainEntity)
        val votes = voteJpaRepository.findAll()

        // then
        votes.size shouldBe 1
        votes[0].schoolId shouldBe savedUserDomainEntity.schoolId
        votes[0].grade shouldBe savedUserDomainEntity.grade
        votes[0].classNumber shouldBe savedUserDomainEntity.classNumber
        votes[0].voteNumber shouldBe 0
    }

    @Test
    fun `가입하지 않은 유저가 투표 생성을 요청할 경우 예외가 발생한다`() {
        // given when
        val shouldThrow = shouldThrow<IllegalArgumentException> { createdVoteService.createVoteByUser(users[0]) }

        // then
        shouldThrow shouldHaveMessage "ID에 해당하는 사용자가 존재하지 않습니다."
    }

    @Test
    fun `오늘의 질문이 잘 생성되는지 확인한다`() {
        val savedUserJpaEntity = userJpaRepository.save(UserMapper.mapToJpaEntity(users[0]))
        val savedUserDomainEntity = UserMapper.mapToDomainEntity(savedUserJpaEntity)

        // when
        createdVoteService.createVoteByUser(savedUserDomainEntity)
        val vote = votePort.findBySchoolIdAndGradeAndClassNumberAndDate(
            users[0].schoolId,
            users[0].grade,
            users[0].classNumber,
            LocalDate.now()
        )

        println(vote)
        println(vote!!.voteIdentifier.schoolId)
        println(vote.voteIdentifier.grade)
        println(vote.voteIdentifier.classNumber)
        println(vote.voteIdentifier.date)
        println(vote.voteNumber)
        println(vote.voteOptionsByVoteDate.voteDate)
        println(vote.voteOptionsByVoteDate.voteOptionsByVoteDate.size)
        println(vote.voteOptionsByVoteDate.voteOptionsByVoteDate[0].id)
        println(vote.voteOptionsByVoteDate.voteOptionsByVoteDate[0].voteId)
        println(vote.voteOptionsByVoteDate.voteOptionsByVoteDate[0].voteOption)
        println(vote.voteOptionsByVoteDate.voteOptionsByVoteDate[1].id)
        println(vote.voteOptionsByVoteDate.voteOptionsByVoteDate[1].voteId)
        println(vote.voteOptionsByVoteDate.voteOptionsByVoteDate[1].voteOption)
        println(vote.voteOptionsByVoteDate.voteOptionsByVoteDate[2].id)
        println(vote.voteOptionsByVoteDate.voteOptionsByVoteDate[2].voteId)
        println(vote.voteOptionsByVoteDate.voteOptionsByVoteDate[2].voteOption)
        println(vote.voteOptionsByVoteDate.voteOptionsByVoteDate[3].id)
        println(vote.voteOptionsByVoteDate.voteOptionsByVoteDate[3].voteId)
        println(vote.voteOptionsByVoteDate.voteOptionsByVoteDate[3].voteOption)
        println(vote.voteOptionsByVoteDate.voteOptionsByVoteDate[4].id)
        println(vote.voteOptionsByVoteDate.voteOptionsByVoteDate[4].voteId)
        println(vote.voteOptionsByVoteDate.voteOptionsByVoteDate[4].voteOption)

        // then
        vote shouldNotBe null
        vote!!.voteIdentifier.schoolId shouldBe savedUserDomainEntity.schoolId
        vote.voteIdentifier.grade shouldBe savedUserDomainEntity.grade
        vote.voteIdentifier.classNumber shouldBe savedUserDomainEntity.classNumber
        vote.voteIdentifier.date shouldBe LocalDate.now()
        vote.voteNumber shouldBe 0
        vote.voteOptionsByVoteDate.voteDate shouldBe LocalDate.now()
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate.size shouldBe 5
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[0].id shouldNotBe null
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[0].voteId shouldBe vote.id
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[0].voteOption shouldBe voteOptions[0]
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[1].id shouldNotBe null
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[1].voteId shouldBe vote.id
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[1].voteOption shouldBe voteOptions[1]
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[2].id shouldNotBe null
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[2].voteId shouldBe vote.id
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[2].voteOption shouldBe voteOptions[2]
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[3].id shouldNotBe null
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[3].voteId shouldBe vote.id
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[3].voteOption shouldBe voteOptions[3]
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[4].id shouldNotBe null
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[4].voteId shouldBe vote.id
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[4].voteOption shouldBe voteOptions[4]
    }

}

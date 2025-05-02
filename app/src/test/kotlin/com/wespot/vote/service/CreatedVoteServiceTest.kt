package com.wespot.vote.service

import com.wespot.common.service.ServiceTest
import com.wespot.exception.CustomException
import com.wespot.school.School
import com.wespot.school.SchoolJpaRepository
import com.wespot.school.fixture.SchoolFixture
import com.wespot.user.User
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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer
import java.time.LocalDate

class CreatedVoteServiceTest @Autowired constructor(
    private var createdVoteService: CreatedVoteService,
    private var userJpaRepository: UserJpaRepository,
    private var voteJpaRepository: VoteJpaRepository,
    private var voteOptionJpaRepository: VoteOptionJpaRepository,
    private var voteOptionPort: VoteOptionPort,
    private var votePort: VotePort,
    private val schoolJpaRepository: SchoolJpaRepository,
) : ServiceTest() {

    private var voteOptions: List<VoteOption> = mutableListOf()
    private var users: List<User> = mutableListOf()

    @BeforeEach
    fun setUp() {
        voteOptions = (1..10).map {
            voteOptionJpaRepository.save(VoteOptionMapper.mapToJpaEntity(VoteOptionFixture.create()))
        }.map { VoteOptionMapper.mapToDomainEntity(it) }
        val school = schoolJpaRepository.save(SchoolFixture.generateJpaEntity())

        users = listOf(
            UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber("Test0@Kakao", schoolId = school.id, 1, 1),
            UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber("Test1@Kakao", schoolId = school.id, 1, 1),
            UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber("Test2@Kakao", schoolId = school.id, 1, 1),
            UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber("Test3@Kakao", schoolId = school.id, 1, 1),
            UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber("Test4@Kakao", schoolId = school.id, 1, 2),
            UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber("Test5@Kakao", schoolId = school.id, 1, 2),
            UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber("Test6@Kakao", schoolId = school.id, 1, 2),
            UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber("Test7@Kakao", schoolId = school.id, 1, 3),
            UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber("Test8@Kakao", schoolId = school.id, 1, 3),
            UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber("Test9@Kakao", schoolId = school.id, 1, 4),
            UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber("Test10@Kakao", schoolId = school.id, 1, 5),
            UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber("Test11@Kakao", schoolId = school.id, 1, 6),
        )
    }

    @Test
    fun `학급당 하루에 하나만의 투표를 만든다`() {
        // given
        userJpaRepository.saveAll(
            users.map { UserMapper.mapToJpaEntity(it) }
                .take(12)
        )

        // when
        createdVoteService.createVotes()
        val votes = voteJpaRepository.findAll()
            .sortedBy { it.classNumber }

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
        votes[0].schoolId shouldBe users[0].school.id
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
        votes[1].schoolId shouldBe users[0].school.id
        votes[1].grade shouldBe 1
        votes[1].classNumber shouldBe 1
        votes[1].voteNumber shouldBe 1
    }

    @Test
    fun `학급에 처음으로 가입한 회원이 존재하는 경우 투표를 생성한다`() {
        // given
        userJpaRepository.deleteAll()
        val savedUserJpaEntity = userJpaRepository.save(UserMapper.mapToJpaEntity(users[0]))
        val savedSchoolJpaEntity =
            schoolJpaRepository.save(SchoolFixture.generateJpaEntity(id = savedUserJpaEntity.schoolId))
        val savedUserDomainEntity = UserMapper.mapToDomainEntity(savedUserJpaEntity, savedSchoolJpaEntity)

        // when
        createdVoteService.createVoteByUser(savedUserDomainEntity)
        val votes = voteJpaRepository.findAll()

        // then
        votes.size shouldBe 1
        votes[0].schoolId shouldBe savedUserDomainEntity.school.id
        votes[0].grade shouldBe savedUserDomainEntity.grade
        votes[0].classNumber shouldBe savedUserDomainEntity.classNumber
        votes[0].voteNumber shouldBe 0
    }

    @Test
    fun `학급에 처음으로 가입하지 않는 경우 투표를 더 이상 생성하지 않는다`() {
        // given
        userJpaRepository.deleteAll()
        val savedUserJpaEntity1 = userJpaRepository.save(UserMapper.mapToJpaEntity(users[0]))
        val savedUserJpaEntity2 = userJpaRepository.save(UserMapper.mapToJpaEntity(users[1]))
        val savedSchoolJpaEntity1 =
            schoolJpaRepository.save(SchoolFixture.generateJpaEntity(id = savedUserJpaEntity1.schoolId))
        val savedSchoolJpaEntity2 =
            schoolJpaRepository.save(SchoolFixture.generateJpaEntity(id = savedUserJpaEntity2.schoolId))
        val savedUserDomainEntity1 = UserMapper.mapToDomainEntity(savedUserJpaEntity1, savedSchoolJpaEntity1)
        val savedUserDomainEntity2 = UserMapper.mapToDomainEntity(savedUserJpaEntity2, savedSchoolJpaEntity2)

        // when
        createdVoteService.createVoteByUser(savedUserDomainEntity1)
        createdVoteService.createVoteByUser(savedUserDomainEntity2)
        val votes = voteJpaRepository.findAll()

        // then
        votes.size shouldBe 1
        votes[0].schoolId shouldBe savedUserDomainEntity1.school.id
        votes[0].grade shouldBe savedUserDomainEntity1.grade
        votes[0].classNumber shouldBe savedUserDomainEntity1.classNumber
        votes[0].voteNumber shouldBe 0
    }

    @Test
    fun `가입하지 않은 유저가 투표 생성을 요청할 경우 예외가 발생한다`() {
        // given when
        val shouldThrow = shouldThrow<CustomException> { createdVoteService.createVoteByUser(users[0]) }

        // then
        shouldThrow shouldHaveMessage "ID에 해당하는 사용자가 존재하지 않습니다."
    }

    @Test
    fun `오늘의 질문이 잘 생성되는지 확인한다`() {
        userJpaRepository.deleteAll()
        val savedUserJpaEntity = userJpaRepository.save(UserMapper.mapToJpaEntity(users[0]))
        val savedSchoolJpaEntity =
            schoolJpaRepository.save(SchoolFixture.generateJpaEntity(id = savedUserJpaEntity.schoolId))
        val savedUserDomainEntity = UserMapper.mapToDomainEntity(savedUserJpaEntity, savedSchoolJpaEntity)

        // when
        val today = LocalDate.now()
        createdVoteService.createVoteByUser(savedUserDomainEntity)
        val vote = votePort.findBySchoolIdAndGradeAndClassNumberAndDate(
            users[0].school.id,
            users[0].grade,
            users[0].classNumber,
            today
        )

        // then
        vote shouldNotBe null
        vote!!.voteIdentifier.schoolId shouldBe savedUserDomainEntity.school.id
        vote.voteIdentifier.grade shouldBe savedUserDomainEntity.grade
        vote.voteIdentifier.classNumber shouldBe savedUserDomainEntity.classNumber
        vote.voteIdentifier.date shouldBe today
        vote.voteNumber shouldBe 0
        vote.voteOptionsByVoteDate.voteDate shouldBe today
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate.size shouldBe 5
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[0].id shouldNotBe null
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[0].voteId shouldBe vote.id
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[0].voteOption.id shouldBe voteOptions[0].id
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[0].voteOption.content shouldBe voteOptions[0].content
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[1].id shouldNotBe null
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[1].voteId shouldBe vote.id
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[1].voteOption.id shouldBe voteOptions[1].id
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[1].voteOption.content shouldBe voteOptions[1].content
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[2].id shouldNotBe null
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[2].voteId shouldBe vote.id
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[2].voteOption.id shouldBe voteOptions[2].id
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[2].voteOption.content shouldBe voteOptions[2].content
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[3].id shouldNotBe null
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[3].voteId shouldBe vote.id
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[3].voteOption.id shouldBe voteOptions[3].id
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[3].voteOption.content shouldBe voteOptions[3].content
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[4].id shouldNotBe null
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[4].voteId shouldBe vote.id
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[4].voteOption.id shouldBe voteOptions[4].id
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate[4].voteOption.content shouldBe voteOptions[4].content
    }

}

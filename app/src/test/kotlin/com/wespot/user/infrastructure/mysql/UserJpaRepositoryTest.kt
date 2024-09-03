package com.wespot.user.infrastructure.mysql

import com.wespot.common.service.ServiceTest
import com.wespot.school.SchoolJpaRepository
import com.wespot.school.SchoolMapper
import com.wespot.school.fixture.SchoolFixture
import com.wespot.user.RestrictionType
import com.wespot.user.fixture.ProfileFixture
import com.wespot.user.fixture.UserFixture
import com.wespot.user.port.out.UserPort
import com.wespot.user.repository.UserJpaRepository
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import kotlin.test.Test

class UserJpaRepositoryTest @Autowired constructor(
    private val userPort: UserPort,
    private val userJpaRepository: UserJpaRepository,
    private val schoolJpaRepository: SchoolJpaRepository
) : ServiceTest() {

    @Test
    fun `친구를 검색할 때, 탈퇴 및 이용제재에 걸려있는 친구와 본인은 조회하지 않는다`() {
        // given
        val school = SchoolFixture.createWithId(0)
        val savedSchool = schoolJpaRepository.save(SchoolMapper.mapToJpaEntity(school))
        val user1 = userPort.save(
            UserFixture.createUser(
                0,
                "hello@kakao",
                "hello",
                savedSchool.id,
                0
            )
        )
        val user2 = userPort.save(
            UserFixture.createUser(
                0,
                "hello4@kakao",
                "hello4",
                savedSchool.id,
                0
            )
        )
        val restrictionUser = userPort.save(
            UserFixture.createUserWithNameAndEmailAndRestrictionTypeAndRestrictDayAndSchoolId(
                "hell",
                "hello1@kakao",
                listOf(Pair(RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT, 30)),
                savedSchool.id
            )
        )
        val withDrawUser = userPort.save(
            UserFixture.createUser(
                0,
                "hello2@kakao",
                "helloo",
                savedSchool.id,
                0
            ).withdraw().completeWithdraw(ProfileFixture.createWithId(0))
        )

        // when
        val searchUsers = userJpaRepository.searchUsers(
            "he",
            "hello",
            "Test School",
            2,
            0,
            user1.id,
            PageRequest.of(0, 10),
        )

        // then
        searchUsers.size shouldBe 1
        searchUsers[0].id shouldBe user2.id
    }

    @Test
    fun `탈퇴 및 이용제재가 아닌 유저만 반 친구로 조회한다`() {
        // given
        val school = SchoolFixture.createWithId(0)
        val savedSchool = schoolJpaRepository.save(SchoolMapper.mapToJpaEntity(school))
        val user1 = userPort.save(
            UserFixture.createUser(
                0,
                "hello@kakao",
                "hello",
                savedSchool.id,
                0
            )
        )
        UserFixture.setSecurityContextUser(user1)
        val user2 = userPort.save(
            UserFixture.createUser(
                0,
                "hello4@kakao",
                "hello4",
                savedSchool.id,
                0
            )
        )
        val restrictionUser = userPort.save(
            UserFixture.createUserWithNameAndEmailAndRestrictionTypeAndRestrictDayAndSchoolId(
                "hell",
                "hello1@kakao",
                listOf(Pair(RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT, 30)),
                savedSchool.id
            )
        )
        val withDrawUser = userPort.save(
            UserFixture.createUser(
                0,
                "hello1@kakao",
                "helloo",
                savedSchool.id,
                0
            ).withdraw().completeWithdraw(ProfileFixture.createWithId(0))
        )

        // when
        val classmateWithoutRegulationUser = userJpaRepository.findAllBySchoolIdAndGradeAndClassNumber(
            savedSchool.id,
            1,
            1
        )

        // then
        classmateWithoutRegulationUser.size shouldBe 2
        classmateWithoutRegulationUser[0].id shouldBe user1.id
        classmateWithoutRegulationUser[1].id shouldBe user2.id
    }

}

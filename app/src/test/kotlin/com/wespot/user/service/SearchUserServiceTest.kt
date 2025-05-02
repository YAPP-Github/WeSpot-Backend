package com.wespot.user.service

import com.wespot.common.service.ServiceTest
import com.wespot.exception.CustomException
import com.wespot.school.SchoolJpaRepository
import com.wespot.school.SchoolMapper
import com.wespot.school.fixture.SchoolFixture
import com.wespot.user.RestrictionType
import com.wespot.user.fixture.ProfileFixture
import com.wespot.user.fixture.UserFixture
import com.wespot.user.port.out.UserPort
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.Test

class SearchUserServiceTest @Autowired constructor(
    private val searchUserService: SearchUserService,
    private val schoolJpaRepository: SchoolJpaRepository,
    private val userPort: UserPort,
) : ServiceTest() {

    @Test
    fun `탈퇴 및 제재를 당한 유저가 검색을 하는 경우 예외가 발생한다`() {
        // given
        val school = SchoolFixture.createWithId(0)
        val savedSchool = schoolJpaRepository.save(SchoolMapper.mapToJpaEntity(school))
        val restrictionUser = userPort.save(
            UserFixture.createUserWithNameAndEmailAndRestrictionTypeAndRestrictDayAndSchoolId(
                "hell",
                "hello1@kakao",
                listOf(Pair(RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT, 30)),
                savedSchool
            )
        )
        val withDrawUser = userPort.save(
            UserFixture.createUser(
                0,
                "hello2@kakao",
                "helloo",
                savedSchool,
                0
            ).withdraw().completeWithdraw(ProfileFixture.createWithId(0))
        )

        // when
        UserFixture.setSecurityContextUser(restrictionUser)
        val shouldThrow1 = shouldThrow<CustomException> {
            searchUserService.searchUsers(
                "he",
                0
            )
        }
        UserFixture.setSecurityContextUser(withDrawUser)
        val shouldThrow2 = shouldThrow<CustomException> {
            searchUserService.searchUsers(
                "he",
                0
            )
        }

        // then
        shouldThrow1 shouldHaveMessage "규제를 당한 유저는 해당 서비스를 사용할 수 없습니다."
        shouldThrow2 shouldHaveMessage "규제를 당한 유저는 해당 서비스를 사용할 수 없습니다."
    }

    @Test
    fun `탈퇴 및 제재를 당하지 않은 유저가 검색을 하는 경우 예외가 발생하지 않는다`() {
        // given
        val school = SchoolFixture.createWithId(0)
        val savedSchool = schoolJpaRepository.save(SchoolMapper.mapToJpaEntity(school))
        val user1 = userPort.save(
            UserFixture.createUser(
                0,
                "hello@kakao",
                "hello",
                savedSchool,
                0
            )
        )
        val user2 = userPort.save(
            UserFixture.createUser(
                0,
                "hello2@kakao",
                "hello2",
                savedSchool,
                0
            )
        )
        val restrictionUser = userPort.save(
            UserFixture.createUserWithNameAndEmailAndRestrictionTypeAndRestrictDayAndSchoolId(
                "hell",
                "hello1@kakao",
                listOf(Pair(RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT, 30)),
                savedSchool
            )
        )
        val withDrawUser = userPort.save(
            UserFixture.createUser(
                0,
                "hello2@kakao",
                "helloo",
                savedSchool,
                0
            ).withdraw().completeWithdraw(ProfileFixture.createWithId(0))
        )

        // when
        UserFixture.setSecurityContextUser(user1)
        val searchUsers = searchUserService.searchUsers(
            "he",
            0
        )

        // then
        searchUsers.users.size shouldBe 1
        searchUsers.hasNext shouldBe false
        searchUsers.users[0].id shouldBe user2.id
    }

}

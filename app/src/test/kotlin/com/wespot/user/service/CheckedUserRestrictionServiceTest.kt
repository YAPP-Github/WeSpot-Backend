package com.wespot.user.service

import com.wespot.common.service.ServiceTest
import com.wespot.user.RestrictionType
import com.wespot.user.fixture.UserFixture
import com.wespot.user.port.out.UserPort
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate
import kotlin.test.Test

class CheckedUserRestrictionServiceTest @Autowired constructor(
    private val userPort: UserPort,
    private val checkedUserRestrictionService: CheckedUserRestrictionService
) : ServiceTest() {

    @Test
    fun `사용자가 제한을 당하지 않은 것을 확인한다`() {
        // given
        val user = UserFixture.createWithId(0)
        val savedUser = userPort.save(user)
        UserFixture.setSecurityContextUser(savedUser)

        // when
        val response = checkedUserRestrictionService.getUserRestriction()

        // then
        response.restrictionType shouldBe RestrictionType.NONE
        response.releaseDate shouldBe LocalDate.of(9999, 12, 31).toString()
    }

    @Test
    fun `사용자가 쪽지와 투표로 인해 영구제재를 당했을 때, 쪽지에 대한 제재 상황을 반환받는다`() {
        // given
        val user =
            UserFixture.createUserWithRestrictionTypeAndRestrictDay(
                listOf(
                    Pair(
                        RestrictionType.PERMANENT_BAN_MESSAGE_REPORT,
                        Long.MAX_VALUE
                    ),
                    Pair(
                        RestrictionType.PERMANENT_BAN_MESSAGE_REPORT,
                        Long.MAX_VALUE
                    )
                )
            )
        val savedUser = userPort.save(user)
        UserFixture.setSecurityContextUser(savedUser)

        // when
        val response = checkedUserRestrictionService.getUserRestriction()

        // then
        response.restrictionType shouldBe RestrictionType.PERMANENT_BAN_MESSAGE_REPORT
        response.releaseDate shouldBe LocalDate.of(9999, 12, 31).toString()
    }

    @Test
    fun `사용자가 투표 영구 제재와 쪽지 이용 제재를 받았을 때, 투표로 인해 영구제재를 당한 것을 확인한다`() {
        // given
        val user =
            UserFixture.createUserWithRestrictionTypeAndRestrictDay(
                listOf(
                    Pair(
                        RestrictionType.PERMANENT_BAN_VOTE_REPORT,
                        Long.MAX_VALUE
                    ),
                    Pair(
                        RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT,
                        30,
                    )
                )
            )
        val savedUser = userPort.save(user)
        UserFixture.setSecurityContextUser(savedUser)

        // when
        val response = checkedUserRestrictionService.getUserRestriction()

        // then
        response.restrictionType shouldBe RestrictionType.PERMANENT_BAN_VOTE_REPORT
        response.releaseDate shouldBe LocalDate.of(9999, 12, 31).toString()
    }

    @Test
    fun `사용자가 쪽지로 인해 30일 이용제한을 당한 것을 확인한다`() {
        // given
        val now = LocalDate.now()
        val user =
            UserFixture.createUserWithRestrictionTypeAndRestrictDay(
                listOf(
                    Pair(
                        RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT,
                        30
                    )
                )
            )
        val savedUser = userPort.save(user)
        UserFixture.setSecurityContextUser(savedUser)

        // when
        val response = checkedUserRestrictionService.getUserRestriction()

        // then
        response.restrictionType shouldBe RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT
        response.releaseDate shouldBe now.plusDays(30).toString()
    }

    @Test
    fun `쪽지 90일 제한을 받은 사용자의 제한을 확인한다`() {
        // given
        val now = LocalDate.now()
        val user =
            UserFixture.createUserWithRestrictionTypeAndRestrictDay(
                listOf(
                    Pair(
                        RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT,
                        90
                    )
                )
            )
        val savedUser = userPort.save(user)
        UserFixture.setSecurityContextUser(savedUser)

        // when
        val response = checkedUserRestrictionService.getUserRestriction()

        // then
        response.restrictionType shouldBe RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT
        response.releaseDate shouldBe now.plusDays(90).toString()
    }

}

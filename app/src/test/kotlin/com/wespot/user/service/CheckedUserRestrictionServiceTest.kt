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
        response.messageRestrictionType shouldBe RestrictionType.NONE
        response.messageReleaseDate shouldBe LocalDate.of(9999, 12, 31)
        response.voteRestrictionType shouldBe RestrictionType.NONE
        response.voteReleaseDate shouldBe LocalDate.of(9999, 12, 31)
    }

    @Test
    fun `사용자가 메시지로 인해 제한을 당한 것을 확인한다`() {
        // given
        val user =
            UserFixture.createUserWithRestrictionTypeAndRestrictDay(
                listOf(
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
        response.messageRestrictionType shouldBe RestrictionType.PERMANENT_BAN_MESSAGE_REPORT
        response.messageReleaseDate shouldBe LocalDate.of(9999, 12, 31)
        response.voteRestrictionType shouldBe RestrictionType.NONE
        response.voteReleaseDate shouldBe LocalDate.of(9999, 12, 31)
    }

    @Test
    fun `사용자가 투표로 인해 제한을 당한 것을 확인한다`() {
        // given
        val user =
            UserFixture.createUserWithRestrictionTypeAndRestrictDay(
                listOf(
                    Pair(
                        RestrictionType.PERMANENT_BAN_VOTE_REPORT,
                        Long.MAX_VALUE
                    )
                )
            )
        val savedUser = userPort.save(user)
        UserFixture.setSecurityContextUser(savedUser)

        // when
        val response = checkedUserRestrictionService.getUserRestriction()

        // then
        response.messageRestrictionType shouldBe RestrictionType.NONE
        response.messageReleaseDate shouldBe LocalDate.of(9999, 12, 31)
        response.voteRestrictionType shouldBe RestrictionType.PERMANENT_BAN_VOTE_REPORT
        response.voteReleaseDate shouldBe LocalDate.of(9999, 12, 31)
    }

    @Test
    fun `사용자가 투표, 쪽지로 인해 제한을 당한 것을 확인한다`() {
        // given
        val user =
            UserFixture.createUserWithRestrictionTypeAndRestrictDay(
                listOf(
                    Pair(
                        RestrictionType.PERMANENT_BAN_VOTE_REPORT,
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
        response.messageRestrictionType shouldBe RestrictionType.PERMANENT_BAN_MESSAGE_REPORT
        response.messageReleaseDate shouldBe LocalDate.of(9999, 12, 31)
        response.voteRestrictionType shouldBe RestrictionType.PERMANENT_BAN_VOTE_REPORT
        response.voteReleaseDate shouldBe LocalDate.of(9999, 12, 31)
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
        response.messageRestrictionType shouldBe RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT
        response.messageReleaseDate shouldBe now.plusDays(90)
        response.voteRestrictionType shouldBe RestrictionType.NONE
        response.voteReleaseDate shouldBe LocalDate.of(9999, 12, 31)
    }

}

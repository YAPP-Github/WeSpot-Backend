package com.wespot.report.service

import com.wespot.common.service.ServiceTest
import com.wespot.user.Restriction
import com.wespot.user.RestrictionType
import com.wespot.user.fixture.UserFixture
import com.wespot.user.mapper.UserMapper
import com.wespot.user.repository.UserJpaRepository
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

class RevokeRestrictionServiceTest @Autowired constructor(
    private val revokeRestrictionService: RevokeRestrictionService,
    private val userJpaRepository: UserJpaRepository,
) : ServiceTest() {

    @Test
    fun `이용 제한 기간이 지난 유저는 제한이 풀린다`() {
        // given
        val user = UserFixture.createWithId(0)
        val restriction = Restriction.of(RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT, 30L)
        user.restrict(restriction)
        val savedUser = userJpaRepository.save(UserMapper.mapToJpaEntity(user))
        val now = LocalDate.now().plusDays(31)

        // when
        revokeRestrictionService.revokeRestriction(now)
        val revokeUser = userJpaRepository.findById(savedUser.id).get()

        // then
        revokeUser.restrictionType shouldBe RestrictionType.NONE
    }

    @Test
    fun `제한 기간이 지나지 않은 유저는 제한이 풀리지 않는다`() {
        // given
        val user = UserFixture.createWithId(0)
        val restriction = Restriction.of(RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT, 30L)
        user.restrict(restriction)
        val savedUser = userJpaRepository.save(UserMapper.mapToJpaEntity(user))
        val now = LocalDate.now().plusDays(29)

        // when
        revokeRestrictionService.revokeRestriction(now)
        val revokeUser = userJpaRepository.findById(savedUser.id).get()

        // then
        revokeUser.restrictionType shouldBe RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT
        revokeUser.releaseDate shouldBe now.plusDays(1)
    }

}

package com.wespot.user.service

import com.wespot.common.service.ServiceTest
import com.wespot.user.dto.request.ModifiedSettingRequest
import com.wespot.user.fixture.UserFixture
import com.wespot.user.port.out.UserPort
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserSettingServiceTest @Autowired constructor(
    private val userSettingService: UserSettingService,
    private val userPort: UserPort
) : ServiceTest() {

    @Test
    fun `유저가 알림 설정을 변경한다`() {
        // given
        val user = UserFixture.createWithId(0)
        val savedUser = userPort.save(user)
        UserFixture.setSecurityContextUser(savedUser)
        val modifiedSettingRequest = ModifiedSettingRequest(
            isEnableVoteNotification = true,
            isEnableMessageNotification = false,
            isEnableMarketingNotification = true
        )

        // when
        userSettingService.modifySetting(modifiedSettingRequest)
        val userByFindById = userPort.findById(savedUser.id)!!

        // then
        userByFindById.isEnableVoteNotification() shouldBe true
        userByFindById.isEnableMessageNotification() shouldBe false
        userByFindById.isEnableMarketingNotification() shouldBe true
    }

    @Test
    fun `유저가 알림 설정을 조회한다`() {
        val user = UserFixture.createWithId(0)
        val savedUser = userPort.save(user)
        UserFixture.setSecurityContextUser(savedUser)
        val modifiedSettingRequest = ModifiedSettingRequest(
            isEnableVoteNotification = true,
            isEnableMessageNotification = false,
            isEnableMarketingNotification = true
        )

        // when
        userSettingService.modifySetting(modifiedSettingRequest)
        val setting = userSettingService.getSetting()

        // then
        setting.isEnableVoteNotification shouldBe true
        setting.isEnableMessageNotification shouldBe false
        setting.isEnableMarketingNotification shouldBe true
    }

}

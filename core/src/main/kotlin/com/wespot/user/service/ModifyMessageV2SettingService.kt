package com.wespot.user.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.user.dto.request.MessageV2Setting
import com.wespot.user.port.`in`.ModifyMessageV2SettingUseCase
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ModifyMessageV2SettingService(
    private val userPort: UserPort,
) : ModifyMessageV2SettingUseCase {

    @Transactional
    override fun changeSetting(toChangeSetting: MessageV2Setting) {
        val loginUser = SecurityUtils.getLoginUser(userPort)
        loginUser.changeSettings(
            isEnableMessage = toChangeSetting.isEnableMessage,
        )
        userPort.save(loginUser)
    }

}

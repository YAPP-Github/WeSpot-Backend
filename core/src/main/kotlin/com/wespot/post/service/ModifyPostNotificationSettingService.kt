package com.wespot.post.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.post.dto.request.ModifyPostNotificationSettingRequest
import com.wespot.post.port.`in`.ModifyPostNotificationSettingUseCase
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ModifyPostNotificationSettingService(
    private val userPort: UserPort
) : ModifyPostNotificationSettingUseCase {

    @Transactional
    override fun changeSetting(modifyPostNotificationSettingRequest: ModifyPostNotificationSettingRequest) {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        loginUser.changeSettings(
            isEnablePostNotification = modifyPostNotificationSettingRequest.isEnablePostNotification
        )
        userPort.save(loginUser)
    }

}

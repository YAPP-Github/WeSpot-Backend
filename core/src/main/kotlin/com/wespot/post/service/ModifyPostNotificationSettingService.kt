package com.wespot.post.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.exception.CustomException
import com.wespot.post.dto.request.ModifyPostNotificationSettingRequest
import com.wespot.post.port.`in`.ModifyPostNotificationSettingUseCase
import com.wespot.user.port.out.UserPort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ModifyPostNotificationSettingService(
    private val userPort: UserPort
) : ModifyPostNotificationSettingUseCase {

    @Transactional
    override fun changeSetting(modifyPostNotificationSettingRequest: ModifyPostNotificationSettingRequest) {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)

        if (loginUser.canNotUseCommunity()) {
            throw CustomException(status = HttpStatus.FORBIDDEN, message = "커뮤니티 이용이 제한된 사용자입니다.")
        }

        loginUser.changeSettings(
            isEnablePostNotification = modifyPostNotificationSettingRequest.isEnablePostNotification
        )
        userPort.save(loginUser)
    }

}

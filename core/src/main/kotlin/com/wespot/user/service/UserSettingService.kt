package com.wespot.user.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.user.dto.request.ModifiedSettingRequest
import com.wespot.user.dto.response.UserSettingResponse
import com.wespot.user.port.`in`.UserSettingUseCase
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserSettingService(
    private val userPort: UserPort
) : UserSettingUseCase {

    @Transactional
    override fun modifySetting(modifiedSettingRequest: ModifiedSettingRequest) {
        val loginUser = SecurityUtils.getLoginUser(userPort)
        loginUser.changeSettings(
            isEnableVoteNotification = modifiedSettingRequest.isEnableVoteNotification,
            isEnableMessageNotification = modifiedSettingRequest.isEnableMessageNotification,
            isEnableMarketingNotification = modifiedSettingRequest.isEnableMarketingNotification
        )
        userPort.save(loginUser)
    }

    @Transactional(readOnly = true)
    override fun getSetting(): UserSettingResponse {
        val loginUser = SecurityUtils.getLoginUser(userPort)

        return UserSettingResponse.from(loginUser)
    }

}

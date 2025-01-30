package com.wespot.auth.service

import com.wespot.auth.port.`in`.LogoutUsecase
import com.wespot.user.port.out.FCMPort
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LogoutService(
    private val userPort: UserPort,
    private val fcmPort: FCMPort,
) : LogoutUsecase {

    @Transactional
    override fun logout() {
        val loginUser = SecurityUtils.getLoginUser(userPort)

        loginUser.logout { clearFcmToken -> fcmPort.save(clearFcmToken) }
    }

}

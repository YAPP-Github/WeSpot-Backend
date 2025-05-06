package com.wespot.user.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.user.port.`in`.UsedAnswerMessageUseCase
import com.wespot.user.port.out.UsedAnswerMessagePort
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UsedAnswerMessageService(
    private val userPort: UserPort,
    private val usedAnswerMessagePort: UsedAnswerMessagePort
) : UsedAnswerMessageUseCase {

    @Transactional(readOnly = true)
    override fun isUsedAnswerMessageFeature(): Boolean {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)

        return usedAnswerMessagePort.findByUserId(userId = loginUser.id)
            ?.isUsedAnswerMessageFeature
            ?: false
    }

}

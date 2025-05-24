package com.wespot.message.service.v2

import com.wespot.auth.service.SecurityUtils
import com.wespot.message.dto.response.TitleOfMessageV2Response
import com.wespot.message.port.`in`.GetTitleOfMessageV2UseCase
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetTitleOfMessageV2Service(
    private val userPort: UserPort,
) : GetTitleOfMessageV2UseCase {

    @Transactional(readOnly = true)
    override fun getTitle(): TitleOfMessageV2Response {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)

        return TitleOfMessageV2Response.from(loginUser)
    }

}

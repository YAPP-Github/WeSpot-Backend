package com.wespot.message.service.v2

import com.wespot.auth.service.SecurityUtils
import com.wespot.message.port.`in`.BlockedMessageV2UseCase
import com.wespot.message.port.out.MessageV2Port
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BlockedMessageV2Service(
    private val userPort: UserPort,
    private val messageV2Port: MessageV2Port
) : BlockedMessageV2UseCase {

    @Transactional
    override fun blockMessage(messageId: Long) {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        val message = messageV2Port.findById(id = messageId)
        message.block(viewer = loginUser)
        messageV2Port.save(message)
    }

}

package com.wespot.message.service.v2

import com.wespot.auth.service.SecurityUtils
import com.wespot.exception.CustomException
import com.wespot.message.event.MessageV2BlockedEvent
import com.wespot.message.port.`in`.BlockedMessageV2UseCase
import com.wespot.message.port.out.MessageV2Port
import com.wespot.user.port.out.UserPort
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BlockedMessageV2Service(
    private val userPort: UserPort,
    private val messageV2Port: MessageV2Port,
    private val applicationEventPublisher: ApplicationEventPublisher,
) : BlockedMessageV2UseCase {

    @Transactional
    override fun blockMessage(messageId: Long) {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)

        if (loginUser.canNotUseMessage()) {
            throw CustomException(message = "메시지 기능을 사용할 수 없는 사용자입니다.", status = HttpStatus.FORBIDDEN)
        }

        val message = messageV2Port.findById(id = messageId)
        message.block(viewer = loginUser)
        messageV2Port.save(message)
        val event = MessageV2BlockedEvent(
            sender = loginUser,
            receiver = message.receiverByViewer(viewer = loginUser),
            message = message
        )
        applicationEventPublisher.publishEvent(event)
    }

}

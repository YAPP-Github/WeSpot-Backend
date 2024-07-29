package com.wespot.message.service

import com.wespot.auth.service.SecurityUtils.getLoginUser
import com.wespot.message.port.`in`.DeleteMessageUseCase
import com.wespot.message.port.out.MessagePort
import com.wespot.message.service.MessageFinder.findMessageById
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DeleteMessageService(
    private val messagePort: MessagePort,
    private val userPort: UserPort
): DeleteMessageUseCase {

    override fun deleteMessage(messageId: Long) {

        val loginUser = getLoginUser(userPort = userPort)
        val message = findMessageById(id = messageId, messagePort = messagePort)
        message.validateDeleteMessage(loginUser)

        val softDelete = message.softDelete()
        messagePort.save(softDelete)

    }
}

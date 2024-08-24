package com.wespot.message.service

import com.wespot.message.Message
import com.wespot.message.port.`in`.CreateMessageUseCase
import com.wespot.message.port.out.MessagePort
import com.wespot.user.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateMessageService(
    private val messagePort: MessagePort,
): CreateMessageUseCase {

    @Transactional
    override fun welcomeMessage(loginUser: User) {
        val welcomeMessage = Message.createWelcomeMessage(
            receiverId = loginUser.id,
            receiverName = loginUser.name
        )
        messagePort.save(welcomeMessage)
    }

}


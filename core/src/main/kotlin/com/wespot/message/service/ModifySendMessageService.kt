package com.wespot.message.service

import com.wespot.auth.service.SecurityUtils.getLoginUser
import com.wespot.message.dto.request.UpdateMessageRequest
import com.wespot.message.dto.response.UpdateMessageResponse
import com.wespot.message.port.`in`.ModifyMessageUseCase
import com.wespot.message.port.out.MessagePort
import com.wespot.message.service.MessageFinder.findMessageById
import com.wespot.message.service.MessageFinder.findUserById
import com.wespot.message.service.MessageTimeValidator.validateMessageUpdateTime
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ModifySendMessageService(
    private val messagePort: MessagePort,
    private val userPort: UserPort
) : ModifyMessageUseCase {

    override fun updateMessage(
        messageId: Long,
        updateMessageRequest: UpdateMessageRequest
    ): UpdateMessageResponse {

        val loginUser = getLoginUser(userPort = userPort)
        val receiverUser = findUserById(id = updateMessageRequest.receiverId, userPort = userPort)
        val message = findMessageById(id = messageId, messagePort = messagePort)

        message.validateMessageOwner(loginUser)
        validateMessageUpdateTime()

        val updateMessage = message.updateMessage(
            content = updateMessageRequest.content,
            receiverId = receiverUser.id,
            senderName = updateMessageRequest.senderName
        )
        updateMessage.validateMessageReceiver()

        val saveMessage = messagePort.save(updateMessage)

        return UpdateMessageResponse.from(saveMessage.id)
    }

    override fun readMessage(messageId: Long) {
        val loginUser = getLoginUser(userPort = userPort)
        val message = findMessageById(messageId, messagePort)

        message.validateSentMessage(loginUser)
        val readMessage = message.readMessage()
        messagePort.save(readMessage)
    }

}

package com.wespot.message.service

import com.wespot.auth.service.SecurityUtils.getLoginUser
import com.wespot.message.dto.request.UpdateMessageRequest
import com.wespot.message.dto.response.UpdateMessageResponse
import com.wespot.message.port.`in`.ModifyMessageUseCase
import com.wespot.message.port.out.MessagePort
import com.wespot.message.service.MessageFinder.findMessageById
import com.wespot.message.service.MessageFinder.findUserById
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ModifyMessageService(
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

        val updateMessage = message.updateMessage(
            content = updateMessageRequest.content,
            modifier = loginUser,
            receiverId = receiverUser.id,
            senderName = updateMessageRequest.senderName
        )

        val saveMessage = messagePort.save(updateMessage)

        return UpdateMessageResponse.from(saveMessage.id)
    }

    override fun readMessage(messageId: Long) {
        val loginUser = getLoginUser(userPort = userPort)
        val message = findMessageById(messageId, messagePort)
        val readMessage = message.readMessage(loginUser)

        messagePort.save(readMessage)
    }

}

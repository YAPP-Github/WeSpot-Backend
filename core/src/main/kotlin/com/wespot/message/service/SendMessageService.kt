package com.wespot.message.service

import com.wespot.auth.service.SecurityUtils.getLoginUser
import com.wespot.message.Message
import com.wespot.message.dto.request.SendMessageRequest
import com.wespot.message.dto.response.SendMessageResponse
import com.wespot.message.port.`in`.SendMessageUseCase
import com.wespot.message.port.out.MessagePort
import com.wespot.message.service.MessageFinder.findUserById
import com.wespot.message.service.MessageSendValidator.validateAlreadySentMessageToday
import com.wespot.message.service.MessageSendValidator.validateSendMessageLimit
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SendMessageService(
    private val messagePort: MessagePort,
    private val userPort: UserPort
) : SendMessageUseCase {

    override fun send(sendMessageRequest: SendMessageRequest): SendMessageResponse {

        val loginUser = getLoginUser(userPort = userPort)
        val receiver = findUserById(id = sendMessageRequest.receiverId, userPort = userPort)

        validateSendMessageLimit(user = loginUser, messagePort = messagePort)
        validateAlreadySentMessageToday(senderId = loginUser.id, receiverId = receiver.id, messagePort = messagePort)
        validateUserBlockStatus()

        val sendMessage = Message.sendMessage(
            content = sendMessageRequest.content,
            receiverId = receiver.id,
            senderId = loginUser.id,
            senderName = sendMessageRequest.senderName
        )

        val saveMessage = messagePort.save(sendMessage)

        return SendMessageResponse.from(saveMessage.id)
    }

    private fun validateUserBlockStatus() {
        //TODO: 차단, 정지 확인
    }

}

package com.wespot.message.service.v2

import com.wespot.auth.service.SecurityUtils
import com.wespot.message.dto.request.AnswerMessageRequest
import com.wespot.message.port.`in`.AnswerMessageV2UseCase
import com.wespot.message.port.out.MessageV2Port
import com.wespot.message.v2.MessageRoom
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AnswerMessageV2Service(
    private val userPort: UserPort,
    private val messageV2Port: MessageV2Port
) : AnswerMessageV2UseCase {

    @Transactional
    override fun answerMessage(messageRoomId: Long, answerMessageRequest: AnswerMessageRequest) {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)

        val message = messageV2Port.findById(id = messageRoomId)
        val messageDetails = messageV2Port.findAllByMessageRoomId(message.id)
        val room = MessageRoom.of(viewer = loginUser, roomMessage = message, messages = messageDetails)

        val answerMessage = room.answer(
            sender = loginUser,
            content = answerMessageRequest.content,
        )
        messageV2Port.save(answerMessage)
    }


}

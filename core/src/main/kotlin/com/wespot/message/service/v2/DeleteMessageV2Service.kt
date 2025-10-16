package com.wespot.message.service.v2

import com.wespot.auth.service.SecurityUtils
import com.wespot.exception.CustomException
import com.wespot.message.port.`in`.DeleteMessageV2UseCase
import com.wespot.message.port.out.MessageV2Port
import com.wespot.message.v2.MessageRoom
import com.wespot.message.v2.MessageV2
import com.wespot.user.port.out.UserPort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteMessageV2Service(
    private val userPort: UserPort,
    private val messageV2Port: MessageV2Port
) : DeleteMessageV2UseCase {

    @Transactional
    override fun deleteMessage(messageId: Long) {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)

        if (loginUser.canNotUseMessage()) {
            throw CustomException(message = "메시지 기능을 사용할 수 없는 사용자입니다.", status = HttpStatus.FORBIDDEN)
        }

        val message = messageV2Port.findById(id = messageId)
        val allMessagesOfRoom = getAllMessagesOfRoom(message)
        val noMeaningNumber = 0

        val room = MessageRoom.of(
            viewer = loginUser,
            alreadyUsedMessageOnToday = noMeaningNumber,
            allMessagesOfRoom = allMessagesOfRoom,
        )
        val deleteMessage = room.deleteMessage(messageId = messageId)
        messageV2Port.save(messageV2 = deleteMessage)
    }

    private fun getAllMessagesOfRoom(message: MessageV2): List<MessageV2> {
        if (message.isRoom()) {
            return listOf(message) + messageV2Port.findAllByMessageRoomId(message.id)
        }

        val messageRoom = messageV2Port.findById(id = message.messageRoomId!!)
        return listOf(messageRoom) + messageV2Port.findAllByMessageRoomId(messageRoom.id)
    }

}

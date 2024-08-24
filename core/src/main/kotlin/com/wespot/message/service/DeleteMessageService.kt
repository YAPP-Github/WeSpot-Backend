package com.wespot.message.service

import com.wespot.auth.service.SecurityUtils.getLoginUser
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.message.Message
import com.wespot.message.port.`in`.DeleteMessageUseCase
import com.wespot.message.port.out.MessagePort
import com.wespot.message.service.MessageFinder.findMessageById
import com.wespot.user.User
import com.wespot.user.port.out.UserPort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DeleteMessageService(
    private val messagePort: MessagePort,
    private val userPort: UserPort
) : DeleteMessageUseCase {

    companion object {
        const val NO_PERMISSION_MESSAGE = "메시지 삭제 권한이 없습니다."
    }

    override fun deleteMessage(messageId: Long) {
        val loginUser = getLoginUser(userPort)
        val message = findMessageById(messageId, messagePort)
        val softDeletedMessage = performSoftDelete(message, loginUser)
        messagePort.save(softDeletedMessage)
    }

    private fun performSoftDelete(message: Message, loginUser: User): Message {
        return when (loginUser.id) {
            message.senderId -> message.sendMessageSoftDelete(loginUser)
            message.receiverId -> message.receivedMessageSoftDelete(loginUser)
            else -> throw CustomException(HttpStatus.UNAUTHORIZED, ExceptionView.TOAST, NO_PERMISSION_MESSAGE)
        }
    }
}

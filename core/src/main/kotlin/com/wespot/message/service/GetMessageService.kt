package com.wespot.message.service

import com.wespot.CursorUtils.getEffectiveCursorId
import com.wespot.auth.service.SecurityUtils.getLoginUser
import com.wespot.message.Message
import com.wespot.message.MessageType
import com.wespot.message.dto.response.MessageListResponse
import com.wespot.message.dto.response.MessageResponse
import com.wespot.message.dto.response.SendMessageStatusResponse
import com.wespot.message.port.`in`.GetMessageUseCase
import com.wespot.message.port.out.MessagePort
import com.wespot.message.service.MessageFinder.findMessageById
import com.wespot.message.service.MessageFinder.findSchoolById
import com.wespot.message.service.MessageFinder.findUserById
import com.wespot.message.service.MessageSendLimitValidator.validateSendMessageLimit
import com.wespot.school.port.out.SchoolPort
import com.wespot.user.port.out.UserPort
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class GetMessageService(
    private val messagePort: MessagePort,
    private val userPort: UserPort,
    private val schoolPort: SchoolPort
) : GetMessageUseCase {

    companion object{
        const val MESSAGE_LIMIT = 3
    }
    override fun getMessage(messageId: Long): MessageResponse {
        val loginUser = getLoginUser(userPort = userPort)
        val receiver = findUserById(id = loginUser.id, userPort = userPort)
        val receiverSchool = findSchoolById(schoolId = receiver.schoolId, schoolPort = schoolPort)
        val message = findMessageById(id = messageId, messagePort = messagePort)

        return MessageResponse.from(
            message = message,
            receiver = receiver,
            school = receiverSchool
        )
    }

    override fun getReceivedMessages(cursorId: Long): MessageListResponse {
        return getMessageListResponse(cursorId) { userId, cursorValue, pageRequest ->
            messagePort.findAllMessagesByTypeAndReceiverAfterCursor(
                messageType = MessageType.RECEIVED,
                receiverId = userId,
                cursorId = cursorValue,
                pageable = pageRequest
            )
        }
    }

    override fun getSendMessages(cursorId: Long): MessageListResponse {
        return getMessageListResponse(cursorId) { userId, cursorValue, pageRequest ->
            messagePort.findAllMessagesByTypeAndSenderAfterCursor(
                messageType = MessageType.SENT,
                senderId = userId,
                cursorId = cursorValue,
                pageable = pageRequest
            )
        }
    }

    override fun status(): SendMessageStatusResponse {
        val loginUser = getLoginUser(userPort = userPort)
        val countSendMessage = validateSendMessageLimit(user = loginUser, messagePort = messagePort)
        val limit = MESSAGE_LIMIT - countSendMessage

        return SendMessageStatusResponse(
            isSendAllowed = limit > 0,
            remainingMessages = limit
        )
    }

    private fun getMessageListResponse(
        cursorId: Long,
        fetchMessages: (Long, Long, PageRequest) -> List<Message>
    ): MessageListResponse {
        val cursorValue = getEffectiveCursorId(cursorId)
        val loginUser = getLoginUser(userPort = userPort)
        val receiver = findUserById(id = loginUser.id, userPort = userPort)
        val receiverSchool = findSchoolById(schoolId = receiver.schoolId, schoolPort = schoolPort)
        val pageRequest = PageRequest.of(0, 10, Sort.by("id").descending())
        val messages = fetchMessages(loginUser.id, cursorValue, pageRequest)

        return MessageListResponse.from(
            messages = messages.map { message ->
                MessageResponse.from(
                    message = message,
                    receiver = receiver,
                    school = receiverSchool
                )
            }
        )
    }

}

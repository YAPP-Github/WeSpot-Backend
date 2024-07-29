package com.wespot.message.service

import com.wespot.CursorUtils.getEffectiveCursorId
import com.wespot.auth.service.SecurityUtils.getLoginUser
import com.wespot.message.MessageType
import com.wespot.message.dto.response.MessageListResponse
import com.wespot.message.dto.response.MessageResponse
import com.wespot.message.dto.response.MessageSimpleListResponse
import com.wespot.message.dto.response.SendMessageStatusResponse
import com.wespot.message.port.`in`.GetMessageUseCase
import com.wespot.message.port.out.MessagePort
import com.wespot.message.service.MessageFinder.findMessageById
import com.wespot.message.service.MessageFinder.findSchoolById
import com.wespot.message.service.MessageFinder.findUserById
import com.wespot.message.service.MessageSendValidator.validateSendMessageLimit
import com.wespot.school.port.out.SchoolPort
import com.wespot.user.port.`in`.GetBlockedUserUseCase
import com.wespot.user.port.out.BlockedUserPort
import com.wespot.user.port.out.UserPort
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class GetMessageService(
    private val messagePort: MessagePort,
    private val userPort: UserPort,
    private val schoolPort: SchoolPort,
    private val blockedUserPort: BlockedUserPort
) : GetMessageUseCase, GetBlockedUserUseCase {

    companion object {
        const val MESSAGE_LIMIT = 3
    }

    override fun getMessage(messageId: Long): MessageResponse {
        val loginUser = getLoginUser(userPort)
        val blockedUserIds = findAllByBlockerId(loginUser.id)
        val message = findMessageById(messageId, messagePort)
        val receiver = findUserById(message.receiverId, userPort)
        val receiverSchool = findSchoolById(receiver.schoolId, schoolPort)

        return MessageResponse.from(
            message = message,
            receiver = receiver,
            school = receiverSchool,
            isBlocked = blockedUserIds.contains(message.senderId))
    }

    override fun getReceivedMessages(cursorId: Long): MessageListResponse {
        val cursorValue = getEffectiveCursorId(cursorId)
        val loginUser = getLoginUser(userPort)
        val blockedUserIds = findAllByBlockerId(loginUser.id)
        val receiver = findUserById(loginUser.id, userPort)
        val receiverSchool = findSchoolById(receiver.schoolId, schoolPort)
        val pageRequest = PageRequest.of(0, 10, Sort.by("id").descending())

        val messages = messagePort.findAllMessagesByTypeAndReceiverAfterCursor(
            messageType = MessageType.RECEIVED,
            receiverId = loginUser.id,
            cursorId = cursorValue,
            blockedUserIds = blockedUserIds,
            pageable = pageRequest
        )

        val hasNext = messagePort.countMessagesAfterCursor(
            messageType = MessageType.RECEIVED,
            receiverId = loginUser.id,
            cursorId = cursorValue,
            blockedIds = blockedUserIds
        ) > 10

        return MessageListResponse.from(
            messages = messages.map { message ->
                MessageResponse.from(
                    message = message,
                    receiver = receiver,
                    school =  receiverSchool,
                    isBlocked = blockedUserIds.contains(message.senderId))
            },
            hasNext = hasNext
        )
    }

    override fun getSendMessages(cursorId: Long): MessageListResponse {
        val cursorValue = getEffectiveCursorId(cursorId)
        val loginUser = getLoginUser(userPort)
        val pageRequest = PageRequest.of(0, 10, Sort.by("id").descending())

        val messages = messagePort.findAllMessagesByTypeAndSenderAfterCursor(
            messageType = MessageType.SENT,
            senderId = loginUser.id,
            cursorId = cursorValue,
            pageable = pageRequest
        )

        val hasNext = messagePort.countSentMessagesAfterCursor(
            messageType = MessageType.SENT,
            senderId = loginUser.id,
            cursorId = cursorValue
        ) > 10

        return MessageListResponse.from(
            messages = messages.map { message ->
                val receiver = findUserById(message.receiverId, userPort)
                val receiverSchool = findSchoolById(receiver.schoolId, schoolPort)
                MessageResponse.from(
                    message = message,
                    receiver = receiver,
                    school = receiverSchool,
                    isBlocked = false
                )
            },
            hasNext = hasNext
        )
    }

    override fun status(): SendMessageStatusResponse {
        val loginUser = getLoginUser(userPort)
        val limit = MESSAGE_LIMIT - validateSendMessageLimit(loginUser, messagePort)

        return SendMessageStatusResponse(
            isSendAllowed = limit > 0,
            remainingMessages = limit
        )
    }

    override fun getScheduledMessages(): MessageSimpleListResponse {
        val loginUser = getLoginUser(userPort)
        val messages = messagePort.findAllScheduledMessages(
            messageType = MessageType.SENT,
            senderId = loginUser.id
        )

        return MessageSimpleListResponse.from(messages = messages.map { message ->
            val receiver = findUserById(message.receiverId, userPort)
            val receiverSchool = findSchoolById(receiver.schoolId, schoolPort)
            MessageResponse.from(
                message = message,
                receiver = receiver,
                school = receiverSchool,
                isBlocked = false
            )
        })

    }

    override fun findAllByBlockerId(blockerId: Long): List<Long> {
        return blockedUserPort.findAllByBlockerId(blockerId).map { it.blockedId }
    }
}

package com.wespot.message.service

import com.wespot.CursorUtils.getEffectiveCursorId
import com.wespot.auth.service.SecurityUtils.getLoginUser
import com.wespot.message.MessageType
import com.wespot.message.dto.response.*
import com.wespot.message.port.`in`.GetMessageUseCase
import com.wespot.message.port.out.MessagePort
import com.wespot.message.service.MessageFinder.findAllByBlockerId
import com.wespot.message.service.MessageFinder.findMessageById
import com.wespot.message.service.MessageFinder.findSchoolById
import com.wespot.message.service.MessageFinder.findUserById
import com.wespot.school.port.out.SchoolPort
import com.wespot.user.Profile
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
) : GetMessageUseCase {

    companion object {
        const val MESSAGE_LIMIT = 3
        const val BAN_PROFILE_ICON_URL = "https://wespot-test-data.s3.ap-northeast-2.amazonaws.com/wespot_ban_profile.png"
    }

    override fun getMessage(messageId: Long): MessageResponse {
        val loginUser = getLoginUser(userPort)
        val blockedUsers = findAllByBlockerId(loginUser.id, blockedUserPort)
        val message = findMessageById(messageId, messagePort)
        message.validateReadMessage(loginUser)
        val receiver = findUserById(message.receiverId, userPort)
        val receiverSchool = findSchoolById(receiver.schoolId, schoolPort)
        val isBlocked = blockedUsers.any { it.messageId == messageId }

        return MessageResponse.from(
            message = message,
            receiver = receiver,
            school = receiverSchool,
            isBlocked = isBlocked
        )
    }

    override fun getReceivedMessages(cursorId: Long): MessageListResponse {
        val cursorValue = getEffectiveCursorId(cursorId)
        val loginUser = getLoginUser(userPort)
        val blockedUsers = findAllByBlockerId(loginUser.id, blockedUserPort)
        val blockedMessageIds= blockedUsers.map { it.messageId }
        val receiver = findUserById(loginUser.id, userPort)
        val receiverSchool = findSchoolById(receiver.schoolId, schoolPort)
        val pageRequest = PageRequest.of(0, 10, Sort.by("id").descending())

        val messages = messagePort.findAllMessagesByTypeAndReceiverAfterCursor(
            receiverId = loginUser.id,
            cursorId = cursorValue,
            blockedMessageIds = blockedMessageIds,
            pageable = pageRequest
        )

        val hasNext = messagePort.countReceivedMessagesAfterCursor(
            receiverId = loginUser.id,
            cursorId = cursorValue,
            blockedMessageIds = blockedMessageIds
        ) > 10

        return MessageListResponse.from(
            messages = messages.map { message ->
                MessageResponse.from(
                    message = message,
                    receiver = receiver,
                    school =  receiverSchool,
                    isBlocked = blockedMessageIds.contains(message.id))
            },
            hasNext = hasNext
        )
    }

    override fun getSendMessages(cursorId: Long): MessageListResponse {
        val cursorValue = getEffectiveCursorId(cursorId)
        val loginUser = getLoginUser(userPort)
        val pageRequest = PageRequest.of(0, 10, Sort.by("id").descending())

        val messages = messagePort.findAllMessagesByTypeAndSenderAfterCursor(
            senderId = loginUser.id,
            cursorId = cursorValue,
            pageable = pageRequest
        )

        val hasNext = messagePort.countSentMessagesAfterCursor(
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
        val sendMessageCount = messagePort.sendMessageCount(loginUser.id)
        val limit = MESSAGE_LIMIT - sendMessageCount
        val blockedUsers = findAllByBlockerId(loginUser.id, blockedUserPort)
        val countUnReadMessages = messagePort.countUnreadMessagesByReceiverId(
            receiverId = loginUser.id,
            blockedMessageIds = blockedUsers.map { it.messageId }
        ).toInt()

        return SendMessageStatusResponse(
            isSendAllowed = limit > 0,
            countRemainingMessages = limit,
            countUnReadMessages = countUnReadMessages
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

    override fun getBlockedMessages(cursorId: Long): MessageBlockedListResponse {
        val cursorValue = getEffectiveCursorId(cursorId)
        val loginUser = getLoginUser(userPort)
        val pageRequest = PageRequest.of(0, 10, Sort.by("id").descending())

        val blockedMessages = blockedUserPort.findAllByBlockerIdAfterCursor(
            blockerId = loginUser.id,
            cursorId = cursorValue,
            pageable = pageRequest
        )

        val messages = blockedMessages.map { blockedUser ->
            val message = findMessageById(blockedUser.messageId, messagePort)
            val receiver = findUserById(message.receiverId, userPort)
            val receiverSchool = findSchoolById(receiver.schoolId, schoolPort)

            MessageBlockedResponse.from(
                message = message,
                senderProfile = Profile(
                    id = 0,
                    backgroundColor ="#FFFFFF",
                    iconUrl = BAN_PROFILE_ICON_URL
                ),
                receiver = receiver,
                school = receiverSchool,
                isBlocked = true
            )
        }

        val hasNext = blockedUserPort.countBlockedUsersAfterCursor(
                blockerId = loginUser.id,
                cursorId = cursorValue,
                pageable = pageRequest
        ) > 10

        return MessageBlockedListResponse.from(
            messages = messages,
            hasNext = hasNext
        )
    }

}

package com.wespot.message.service.v2

import com.wespot.auth.service.SecurityUtils
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.message.dto.request.CreatedMessageV2Request
import com.wespot.message.port.`in`.CreatedMessageV2UseCase
import com.wespot.message.port.out.MessageV2Port
import com.wespot.message.v2.MessageV2
import com.wespot.user.dto.request.CreatedAnonymousProfileRequest
import com.wespot.user.message.AnonymousProfile
import com.wespot.user.port.`in`.AnonymousProfileUseCase
import com.wespot.user.port.out.UserPort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class CreatedMessageV2Service(
    private val userPort: UserPort,
    private val profileUseCase: AnonymousProfileUseCase,
    private val messageV2Port: MessageV2Port,
) : CreatedMessageV2UseCase {

    override fun createMessage(createdMessageV2Request: CreatedMessageV2Request): MessageV2 {
        val sender = SecurityUtils.getLoginUser(userPort)
        val receiver =
            userPort.findById(userId = createdMessageV2Request.receiverId) ?: throw CustomException(
                HttpStatus.NOT_FOUND,
                ExceptionView.TOAST,
                "유저를 찾을 수 없습니다."
            )
        val anonymousProfile = createAnonymousProfile(createdMessageV2Request)

        return MessageV2.createInitial(
            content = createdMessageV2Request.content,
            sender = sender,
            receiver = receiver,
            anonymousProfile = anonymousProfile,
            savedMessageFunction = { message -> messageV2Port.save(message) },
            alreadyUsedMessageOnToday = messageV2Port.countTodaySendMessages(sender.id),
        )
    }

    private fun createAnonymousProfile(createdMessageV2Request: CreatedMessageV2Request): AnonymousProfile? {
        if (createdMessageV2Request.isAnonymous) {
            return profileUseCase.createAnonymousProfile(
                CreatedAnonymousProfileRequest(
                    name = createdMessageV2Request.anonymousProfileName,
                    imageUrl = createdMessageV2Request.anonymousImageUrl,
                    receiverId = createdMessageV2Request.receiverId
                )
            )
        }

        return null
    }


}

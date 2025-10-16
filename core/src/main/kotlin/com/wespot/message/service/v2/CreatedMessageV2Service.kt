package com.wespot.message.service.v2

import com.wespot.auth.service.SecurityUtils
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.message.MessageContent
import com.wespot.message.dto.request.CreatedMessageV2Request
import com.wespot.message.port.`in`.CreatedMessageV2UseCase
import com.wespot.message.port.out.MessageV2Port
import com.wespot.message.v2.MessageV2
import com.wespot.user.User
import com.wespot.user.dto.request.CreatedAnonymousProfileRequest
import com.wespot.user.message.AnonymousProfile
import com.wespot.user.port.`in`.AnonymousProfileUseCase
import com.wespot.user.port.out.UserPort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreatedMessageV2Service(
    private val userPort: UserPort,
    private val profileUseCase: AnonymousProfileUseCase,
    private val messageV2Port: MessageV2Port,
) : CreatedMessageV2UseCase {

    @Transactional
    override fun createMessage(createdMessageV2Request: CreatedMessageV2Request): MessageV2 {
        val sender = SecurityUtils.getLoginUser(userPort)

        if (sender.canNotUseMessage()) {
            throw CustomException(message = "메시지 기능을 사용할 수 없는 사용자입니다.", status = HttpStatus.FORBIDDEN)
        }

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
            isAlreadyExistsRoomTalkWithThisReceiverWithRealName = { user1, user2 ->
                messageV2Port.isExistsBySenderIdAndReceiverIdWithRealName(
                    user1.id,
                    user2.id
                ) ||
                    messageV2Port.isExistsBySenderIdAndReceiverIdWithRealName(
                        user2.id,
                        user1.id
                    )
            },
            savedMessageFunction = { message -> messageV2Port.save(message) },
            alreadyUsedMessageOnToday = messageV2Port.countTodaySendMessages(sender.id),
        )
    }

    private fun createAnonymousProfile(createdMessageV2Request: CreatedMessageV2Request): AnonymousProfile? {
        if (createdMessageV2Request.isAnonymous) {
            return profileUseCase.createAnonymousProfile(
                CreatedAnonymousProfileRequest(
                    name = createdMessageV2Request.anonymousProfileName!!,
                    imageUrl = createdMessageV2Request.anonymousImageUrl!!,
                    receiverId = createdMessageV2Request.receiverId
                )
            )
        }

        return null
    }

    @Transactional
    override fun welcomeMessage(signUpUser: User) {
        val ever = userPort.findByName(name = User.EVER_NAME) ?: throw CustomException(
            message = "해당 계정이 존재하지 않습니다.",
            view = ExceptionView.TOAST,
            status = HttpStatus.NOT_FOUND,
        )

        MessageV2.createInitial(
            content = MessageContent.createWelcomeMessage(receiverName = signUpUser.name).content,
            sender = ever,
            receiver = signUpUser,
            anonymousProfile = null,
            savedMessageFunction = { message -> messageV2Port.save(message) },
            isAlreadyExistsRoomTalkWithThisReceiverWithRealName = { user1, user2 -> false },
            alreadyUsedMessageOnToday = 0,
        )
    }


}

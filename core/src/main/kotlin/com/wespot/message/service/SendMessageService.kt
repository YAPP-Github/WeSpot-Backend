package com.wespot.message.service

import com.wespot.auth.service.SecurityUtils.getLoginUser
import com.wespot.message.Message
import com.wespot.message.MessageTimeValidator.validateMessageSendTime
import com.wespot.message.dto.request.SendMessageRequest
import com.wespot.message.dto.response.SendMessageResponse
import com.wespot.message.event.MessageLimitEvent
import com.wespot.message.port.`in`.SendMessageUseCase
import com.wespot.message.port.out.MessagePort
import com.wespot.message.service.MessageFinder.findUserById
import com.wespot.message.service.MessageSendValidator.validateAlreadySentMessageToday
import com.wespot.message.service.MessageSendValidator.validateSendMessageLimit
import com.wespot.message.service.MessageSendValidator.validateUserBlockStatus
import com.wespot.user.port.out.BlockedUserPort
import com.wespot.user.port.out.UserPort
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SendMessageService(
    private val messagePort: MessagePort,
    private val userPort: UserPort,
    private val blockedUserPort: BlockedUserPort,
    private val eventPublisher: ApplicationEventPublisher
) : SendMessageUseCase {

    override fun send(sendMessageRequest: SendMessageRequest): SendMessageResponse {

        val loginUser = getLoginUser(userPort = userPort)
        val receiver = findUserById(id = sendMessageRequest.receiverId, userPort = userPort)

        validateSendMessageLimit(user = loginUser, messagePort = messagePort)
        validateAlreadySentMessageToday(senderId = loginUser.id, receiverId = receiver.id, messagePort = messagePort)
        validateUserBlockStatus(senderId = loginUser.id, receiverId = receiver.id, blockedUserPort = blockedUserPort)
        validateMessageSendTime()

        val sendMessage = Message.sendMessage(
            content = sendMessageRequest.content,
            receiverId = receiver.id,
            senderId = loginUser.id,
            senderName = sendMessageRequest.senderName,
            isAnonymous = sendMessageRequest.isAnonymous
        )

        val saveMessage = messagePort.save(sendMessage)
        eventPublisher.publishEvent(
            MessageLimitEvent(
                senderId = loginUser.id,
                messagePort.sendMessageCount(loginUser.id)
            )
        ) // TODO : 나중에 메시지 예약 취소하는 경우에도 해당 이벤트 발생시켜주시면 좋을 것 같아요.
//        eventPublisher.publishEvent(ReceivedMessageEvent(receiver, saveMessage.id)) // TODO : 생각해보니 이것은 실제로 발송될 때, 전송되어야 하니 나중에 스케줄링 추가하실 때 해당 이벤트 추가해주실 수 있을까요?

        return SendMessageResponse.from(saveMessage.id)
    }

}

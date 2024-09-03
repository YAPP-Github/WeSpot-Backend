package com.wespot.message.service

import com.wespot.EventUtils
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.message.Message
import com.wespot.message.event.ReceivedMessageEvent
import com.wespot.message.port.`in`.SchedulerMessageUseCase
import com.wespot.message.port.out.MessagePort
import com.wespot.user.port.out.UserPort
import com.wespot.user.service.UserFinder
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class ScheduledMessageSenderService(
    private val messagePort: MessagePort,
    private val userPort: UserPort,
) : SchedulerMessageUseCase {

    @Transactional
    override fun sendScheduledMessages() {
        val yesterday: LocalDateTime = LocalDate.now().atStartOfDay().minusDays(1)
        val sentMessages = messagePort.findByMessageTypeAndSendAtBefore(yesterday)

        sentMessages.forEach { message ->
            processMessage(message)
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun processMessage(message: Message) {
        try {
            val receivedMessage = message.receivedMessage()
            messagePort.save(receivedMessage)

            val receiver = UserFinder.findUserById(
                id = receivedMessage.receiverId,
                userPort = userPort
            )
            EventUtils.publish(
                ReceivedMessageEvent(
                    receiver = receiver,
                    messageId = receivedMessage.id
                )
            )
        } catch (e: Exception) {
            throw CustomException(HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "메시지 발송에 실패했습니다.${message.id}")
        }
    }
}

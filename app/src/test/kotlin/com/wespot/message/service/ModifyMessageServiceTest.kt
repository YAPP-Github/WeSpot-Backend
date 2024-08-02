package com.wespot.message.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.message.Message
import com.wespot.message.MessageTimeValidator
import com.wespot.message.dto.request.UpdateMessageRequest
import com.wespot.message.dto.response.UpdateMessageResponse
import com.wespot.message.event.ReadMessageByReceiverEvent
import com.wespot.message.fixture.MessageFixture
import com.wespot.message.port.out.MessagePort
import com.wespot.user.User
import com.wespot.user.fixture.UserFixture
import com.wespot.user.port.out.UserPort
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.ApplicationEventPublisher
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

class ModifyMessageServiceTest : BehaviorSpec({

    val messagePort = mockk<MessagePort>()
    val userPort = mockk<UserPort>()
    val eventPublisher = mockk<ApplicationEventPublisher>()
    val modifyMessageService = ModifyMessageService(
        messagePort = messagePort,
        userPort = userPort,
        eventPublisher = eventPublisher
    )

    lateinit var sender: User
    lateinit var receiver: User
    lateinit var message: Message
    lateinit var receivedMessage: Message

    beforeContainer {
        // 시간을 조작하여 테스트 시간 설정
        val fixedClock = Clock.fixed(Instant.parse("2023-03-18T18:00:00Z"), ZoneId.of("UTC"))
        MessageTimeValidator.setClock(fixedClock)

        sender = UserFixture.createSender()
        receiver = UserFixture.createReceiver()
        message = MessageFixture.createMessage("Hello", receiver.id, sender.id, sender.name)
        receivedMessage = MessageFixture.createMessageWithReceived("Hello", receiver.id, sender.id, sender.name)

        UserFixture.setSecurityContextUser(sender)
    }

    afterContainer {
        clearAllMocks()
        MessageTimeValidator.resetClock()
    }

    given("ModifySendMessageService") {

        `when`("updateMessage() 메서드를 호출할 때") {
            val updateMessageRequest = UpdateMessageRequest(
                content = "Updated message",
                receiverId = receiver.id,
                senderName = sender.name
            )

            every { userPort.findById(receiver.id) } returns receiver
            every { messagePort.findById(message.id) } returns message
            every { SecurityUtils.getLoginUser(userPort) } returns sender
            every { messagePort.save(any()) } returns message.copy(content = updateMessageRequest.content)

            then("메시지가 올바르게 업데이트되어야 한다") {
                val response = modifyMessageService.updateMessage(message.id, updateMessageRequest)
                response shouldBe UpdateMessageResponse.from(message.id)
            }

            then("올바른 메시지를 가져와야 한다") {
                modifyMessageService.updateMessage(message.id, updateMessageRequest)
                verify { messagePort.findById(message.id) }
            }

            then("로그인한 사용자가 메시지를 업데이트해야 한다") {
                modifyMessageService.updateMessage(message.id, updateMessageRequest)
                verify { SecurityUtils.getLoginUser(userPort) }
            }

            then("메시지를 저장해야 한다") {
                modifyMessageService.updateMessage(message.id, updateMessageRequest)
                verify { messagePort.save(any()) }
            }
        }

        `when`("readMessage() 메서드를 호출할 때") {
            val messageId = message.id

            every { SecurityUtils.getLoginUser(userPort) } returns receiver
            every { userPort.findById(1) } returns sender
            every { messagePort.findById(messageId) } returns receivedMessage
            every { messagePort.save(any()) } returns receivedMessage.copy(isReceiverRead = true)
            every {
                eventPublisher.publishEvent(
                    ReadMessageByReceiverEvent(
                        sender,
                        receiver,
                        messageId,
                        false
                    )
                )
            } returns Unit

            then("메시지가 읽은 상태로 업데이트되어야 한다") {
                modifyMessageService.readMessage(messageId)
                verify { messagePort.save(any()) }
            }

            then("올바른 메시지를 가져와야 한다") {
                modifyMessageService.readMessage(messageId)
                verify { messagePort.findById(messageId) }
            }
        }
    }
})

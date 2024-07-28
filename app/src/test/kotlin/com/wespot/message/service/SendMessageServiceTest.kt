package com.wespot.message.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.message.Message
import com.wespot.message.MessageTimeValidator
import com.wespot.message.dto.request.SendMessageRequest
import com.wespot.message.dto.response.SendMessageResponse
import com.wespot.message.fixture.MessageFixture
import com.wespot.message.port.out.MessagePort
import com.wespot.user.User
import com.wespot.user.fixture.UserFixture
import com.wespot.user.port.out.UserPort
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

class SendMessageServiceTest : BehaviorSpec({

    val messagePort = mockk<MessagePort>()
    val userPort = mockk<UserPort>()
    val sendMessageService = SendMessageService(
        messagePort = messagePort,
        userPort = userPort
    )

    lateinit var sender: User
    lateinit var receiver: User
    lateinit var message: Message

    beforeContainer {
        sender = UserFixture.createSender()
        receiver = UserFixture.createReceiver()
        message = MessageFixture.createMessage("Hello", receiver.id, sender.id, sender.name)

        UserFixture.setSecurityContextUser(sender)
    }

    afterContainer {
        clearAllMocks()
        MessageTimeValidator.resetClock() // Reset clock after tests
    }

    given("SendMessageService") {

        `when`("send() 메서드를 호출할 때") {
            val sendMessageRequest = SendMessageRequest(
                content = "Hello",
                receiverId = receiver.id,
                senderName = "sender"
            )

            every { userPort.findById(sender.id) } returns sender
            every { userPort.findById(receiver.id) } returns receiver
            every { userPort.findByEmail(sender.email) } returns sender
            every { messagePort.save(any()) } returns message
            every { messagePort.sendMessageCount(sender.id) } returns 0
            every { messagePort.hasSentMessageToday(sender.id, receiver.id) } returns false

            // 시간을 조작하여 테스트 시간 설정
            val fixedClock = Clock.fixed(Instant.parse("2023-03-18T18:00:00Z"), ZoneId.of("UTC"))
            MessageTimeValidator.setClock(fixedClock)

            val response = sendMessageService.send(sendMessageRequest)

            then("메시지가 올바르게 전송되어야 한다") {
                response shouldBe SendMessageResponse.from(message.id)
            }

            then("올바른 수신자를 가져와야 한다") {
                verify { userPort.findById(receiver.id) }
            }

            then("로그인한 사용자로 메시지를 보내야 한다") {
                verify { SecurityUtils.getLoginUser(userPort) }
            }

            then("메시지를 저장해야 한다") {
                verify { messagePort.save(any()) }
            }
        }
    }
})

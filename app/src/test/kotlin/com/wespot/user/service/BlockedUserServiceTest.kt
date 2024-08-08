package com.wespot.user.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.message.Message
import com.wespot.message.MessageType
import com.wespot.message.fixture.MessageFixture
import com.wespot.message.port.out.MessagePort
import com.wespot.message.service.MessageFinder
import com.wespot.user.User
import com.wespot.user.block.BlockedUser
import com.wespot.user.dto.response.BlockedUserResponse
import com.wespot.user.fixture.BlockedUserFixture
import com.wespot.user.fixture.UserFixture
import com.wespot.user.port.out.BlockedUserPort
import com.wespot.user.port.out.UserPort
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.mockk.*

class BlockedUserServiceTest : BehaviorSpec({
    val blockedUserPort = mockk<BlockedUserPort>()
    val messagePort = mockk<MessagePort>()
    val userPort = mockk<UserPort>()
    val blockedUserService = BlockedUserService(
        blockedUserPort = blockedUserPort,
        messagePort = messagePort,
        userPort = userPort
    )

    lateinit var sender: User
    lateinit var receiver: User
    lateinit var message: Message

    beforeContainer {
        sender = UserFixture.createSender()
        receiver = UserFixture.createReceiver()
        message = MessageFixture.createMessageWithReceived("Hello", receiver.id, sender.id, sender.name)
        UserFixture.setSecurityContextUser(receiver)
    }

    afterContainer {
        clearAllMocks()
    }

    given("수신된 메시지") {
        `when`("메시지를 차단할 때") {
            every { SecurityUtils.getLoginUser(userPort) } returns receiver
            every { MessageFinder.findMessageById(message.id, messagePort) } returns message
            every { MessageFinder.findUserById(sender.id, userPort) } returns sender
            every { blockedUserPort.existsByBlockerIdAndBlockedIdAndMessageId(receiver.id, sender.id, message.id) } returns false
            every { blockedUserPort.save(any<BlockedUser>()) } returns BlockedUserFixture.createWithIdAndBlockedIdAndBlockerId(1, receiver.id, sender.id, message.id)

            then("사용자를 차단해야 한다") {
                val response = blockedUserService.blockedUser(message.id)
                response shouldBe BlockedUserResponse.of(1)
                verify { blockedUserPort.save(any<BlockedUser>()) }
            }
        }

        `when`("이미 차단된 메시지를 차단할 때") {
            every { SecurityUtils.getLoginUser(userPort) } returns receiver
            every { MessageFinder.findMessageById(message.id, messagePort) } returns message
            every { MessageFinder.findUserById(sender.id, userPort) } returns sender
            every { blockedUserPort.existsByBlockerIdAndBlockedIdAndMessageId(receiver.id, sender.id, message.id) } returns true

            then("예외를 발생시켜야 한다") {
                val exception = shouldThrow<IllegalStateException> {
                    blockedUserService.blockedUser(message.id)
                }
                exception.message shouldBe "이미 차단된 사용자입니다."
            }
        }

        `when`("메시지를 차단 해제할 때") {
            every { SecurityUtils.getLoginUser(userPort) } returns receiver
            every { MessageFinder.findMessageById(message.id, messagePort) } returns message
            every { MessageFinder.findUserById(sender.id, userPort) } returns sender
            every { blockedUserPort.deleteByBlockerIdAndBlockedIdAndMessageId(receiver.id, sender.id, message.id) } just Runs

            then("사용자의 차단을 해제해야 한다") {
                blockedUserService.unblockedUser(message.id)
                verify { blockedUserPort.deleteByBlockerIdAndBlockedIdAndMessageId(receiver.id, sender.id, message.id) }
            }
        }
    }

    given("보낸 메시지") {
        val sentMessage = message.copy(messageType = MessageType.SENT, senderId = receiver.id, receiverId = sender.id)

        `when`("메시지를 차단하려고 할 때") {
            every { SecurityUtils.getLoginUser(userPort) } returns receiver
            every { MessageFinder.findMessageById(sentMessage.id, messagePort) } returns sentMessage

            then("예외를 발생시켜야 한다") {
                val exception = shouldThrow<IllegalArgumentException> {
                    blockedUserService.blockedUser(sentMessage.id)
                }
                exception.message shouldBe "받은 메시지만 차단이 가능합니다."
            }
        }
    }
})

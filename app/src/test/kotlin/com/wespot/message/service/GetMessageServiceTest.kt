package com.wespot.message.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.message.Message
import com.wespot.message.MessageType
import com.wespot.message.dto.response.MessageListResponse
import com.wespot.message.dto.response.MessageResponse
import com.wespot.message.dto.response.SendMessageStatusResponse
import com.wespot.message.fixture.MessageFixture
import com.wespot.message.port.out.MessagePort
import com.wespot.school.School
import com.wespot.school.SchoolType
import com.wespot.school.fixture.SchoolFixture
import com.wespot.school.port.out.SchoolPort
import com.wespot.user.User
import com.wespot.user.fixture.UserFixture
import com.wespot.user.port.out.UserPort
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

class GetMessageServiceTest : BehaviorSpec({

    val messagePort = mockk<MessagePort>()
    val userPort = mockk<UserPort>()
    val schoolPort = mockk<SchoolPort>()
    val getMessageService = GetMessageService(
        messagePort = messagePort,
        userPort = userPort,
        schoolPort = schoolPort
    )

    lateinit var sender: User
    lateinit var receiver: User
    lateinit var school: School

    beforeContainer {
        sender = UserFixture.createSender()
        receiver = UserFixture.createReceiver()
        school = SchoolFixture.createSchool(1L, "서울고등학교", SchoolType.HIGH, "서울", "서울시 강남구")
        UserFixture.setSecurityContextUser(sender)
    }

    afterContainer {
        clearAllMocks()
    }

    fun initializeMessages(): MutableList<Message> {
        val messages = mutableListOf<Message>()
        for (i in 1..11) {
            messages.add(
                MessageFixture.createMessage("Hello $i", receiver.id, sender.id, sender.name).copy(id = i.toLong())
            )
        }
        return messages
    }

    given("GetMessageService") {

        `when`("getMessage() 메서드를 호출할 때") {
            val messages = initializeMessages()
            val messageId = messages[0].id

            every { messagePort.findById(messageId) } returns messages[0]
            every { SecurityUtils.getLoginUser(userPort) } returns receiver
            every { userPort.findById(sender.id) } returns sender
            every { userPort.findById(receiver.id) } returns receiver
            every { schoolPort.findById(receiver.schoolId) } returns school

            then("올바른 메시지를 반환해야 한다") {
                val response = getMessageService.getMessage(messageId)
                response shouldBe MessageResponse.from(
                    message = messages[0],
                    receiver = receiver,
                    school = school
                )
            }

            then("올바른 메시지를 가져와야 한다") {
                getMessageService.getMessage(messageId)
                verify { messagePort.findById(messageId) }
            }
        }

        `when`("첫 페이지의 발신 메시지를 조회하면") {
            val messages = initializeMessages()
            val cursorId = Long.MAX_VALUE
            val pageRequest = PageRequest.of(0, 10, Sort.by("id").descending())

            every { SecurityUtils.getLoginUser(userPort) } returns sender
            every { userPort.findById(sender.id) } returns sender
            every { userPort.findById(receiver.id) } returns receiver
            every { schoolPort.findById(receiver.schoolId) } returns school
            every {
                messagePort.findAllMessagesByTypeAndSenderAfterCursor(
                    MessageType.SENT,
                    sender.id,
                    cursorId,
                    pageRequest
                )
            } returns messages.take(10)

            val result = getMessageService.getSendMessages(cursorId)

            then("첫 10개의 발신 메시지 목록을 반환해야 한다") {
                result.messages.size shouldBe 10
                result shouldBe MessageListResponse.from(messages.take(10).map { message ->
                    MessageResponse.from(
                        message = message,
                        receiver = sender,
                        school = school
                    )
                })
            }
        }

        `when`("커서 기반으로 발신 메시지를 조회하면") {
            val messages = initializeMessages()
            val cursorId = messages[9].id
            val pageRequest = PageRequest.of(0, 10, Sort.by("id").descending())

            every { SecurityUtils.getLoginUser(userPort) } returns sender
            every { userPort.findById(sender.id) } returns sender
            every { userPort.findById(receiver.id) } returns receiver
            every { schoolPort.findById(receiver.schoolId) } returns school
            every {
                messagePort.findAllMessagesByTypeAndSenderAfterCursor(
                    MessageType.SENT,
                    sender.id,
                    cursorId,
                    pageRequest
                )
            } returns messages.drop(10)

            val result = getMessageService.getSendMessages(cursorId)

            then("다음 페이지의 발신 메시지 목록을 반환해야 한다") {
                result.messages.size shouldBe 1
                result shouldBe MessageListResponse.from(messages.drop(10).map { message ->
                    MessageResponse.from(
                        message = message,
                        receiver = sender,
                        school = school
                    )
                })
            }
        }


        `when`("첫 페이지의 수신 메시지를 조회하면") {
            val messages = initializeMessages()
            val cursorId = Long.MAX_VALUE
            val pageRequest = PageRequest.of(0, 10, Sort.by("id").descending())

            every { SecurityUtils.getLoginUser(userPort) } returns receiver // receiver로 변경
            every { userPort.findById(receiver.id) } returns receiver
            every { userPort.findById(sender.id) } returns sender // sender로 변경
            every { schoolPort.findById(receiver.schoolId) } returns school
            every {
                messagePort.findAllMessagesByTypeAndReceiverAfterCursor(
                    MessageType.RECEIVED,
                    receiver.id,
                    cursorId,
                    pageRequest
                )
            } returns messages.take(10)

            val result = getMessageService.getReceivedMessages(cursorId)

            then("첫 10개의 수신 메시지 목록을 반환해야 한다") {
                result.messages.size shouldBe 10
                result shouldBe MessageListResponse.from(messages.take(10).map { message ->
                    MessageResponse.from(
                        message = message,
                        receiver = receiver,
                        school = school
                    )
                })
            }
        }

        `when`("커서 기반으로 수신 메시지를 조회하면") {
            val messages = initializeMessages()
            val cursorId = messages[9].id
            val pageRequest = PageRequest.of(0, 10, Sort.by("id").descending())

            every { SecurityUtils.getLoginUser(userPort) } returns receiver // receiver로 변경
            every { userPort.findById(receiver.id) } returns receiver
            every { userPort.findById(sender.id) } returns sender // sender로 변경
            every { schoolPort.findById(receiver.schoolId) } returns school
            every {
                messagePort.findAllMessagesByTypeAndReceiverAfterCursor(
                    MessageType.RECEIVED,
                    receiver.id,
                    cursorId,
                    pageRequest
                )
            } returns messages.drop(10)

            val result = getMessageService.getReceivedMessages(cursorId)

            then("다음 페이지의 수신 메시지 목록을 반환해야 한다") {
                result.messages.size shouldBe 1
                result shouldBe MessageListResponse.from(messages.drop(10).map { message ->
                    MessageResponse.from(
                        message = message,
                        receiver = receiver,
                        school = school
                    )
                })
            }
        }

        `when`("status() 메서드를 호출할 때") {
            every { SecurityUtils.getLoginUser(userPort) } returns sender
            every { messagePort.sendMessageCount(sender.id) } returns 0

            then("SendMessageStatusResponse를 반환해야 한다") {
                val response = getMessageService.status()
                response shouldBe SendMessageStatusResponse(
                    isSendAllowed = true,
                    remainingMessages = 3
                )
            }

            then("메시지 전송 한도를 검증해야 한다") {
                getMessageService.status()
                verify { messagePort.sendMessageCount(sender.id) }
            }
        }
    }
})

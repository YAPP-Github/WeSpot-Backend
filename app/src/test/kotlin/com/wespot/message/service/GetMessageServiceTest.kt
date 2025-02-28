package com.wespot.message.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.message.Message
import com.wespot.message.MessageTimeValidator
import com.wespot.message.dto.response.*
import com.wespot.message.fixture.MessageFixture
import com.wespot.message.port.out.MessagePort
import com.wespot.school.School
import com.wespot.school.SchoolType
import com.wespot.school.fixture.SchoolFixture
import com.wespot.school.port.out.SchoolPort
import com.wespot.user.User
import com.wespot.user.fixture.BlockedUserFixture
import com.wespot.user.fixture.UserFixture
import com.wespot.user.port.out.BlockedUserPort
import com.wespot.user.port.out.UserPort
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.mockito.BDDMockito
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

class GetMessageServiceTest : BehaviorSpec({

    val messagePort = mockk<MessagePort>()
    val userPort = mockk<UserPort>()
    val schoolPort = mockk<SchoolPort>()
    val blockedUserPort = mockk<BlockedUserPort>()
    val getMessageService = GetMessageService(
        messagePort = messagePort,
        userPort = userPort,
        schoolPort = schoolPort,
        blockedUserPort = blockedUserPort
    )

    lateinit var sender: User
    lateinit var blockedSender: User
    lateinit var receiver: User
    lateinit var school: School

    beforeContainer {
        // 시간을 조작하여 테스트 시간 설정
        val fixedClock = Clock.fixed(Instant.parse("2023-03-18T18:00:00Z"), ZoneId.of("UTC"))
        MessageTimeValidator.setClock(fixedClock)

        sender = UserFixture.createSender()
        blockedSender = UserFixture.createWithId(3)
        receiver = UserFixture.createReceiver()
        school = SchoolFixture.createSchool(1L, "서울고등학교", SchoolType.HIGH, "서울", "서울시 강남구")
        UserFixture.setSecurityContextUser(receiver)
    }

    afterContainer {
        clearAllMocks()
    }

    fun initializeMessages(): MutableList<Message> {
        val messages = mutableListOf<Message>()
        for (i in 1..6) {
            messages.add(
                MessageFixture.createMessage("Hello $i", receiver.id, sender.id, sender.name).copy(id = i.toLong())
            )
        }
        for (i in 7..11) {
            messages.add(
                MessageFixture.createMessageWithReceived("Hello $i", receiver.id, blockedSender.id, sender.name)
                    .copy(id = i.toLong())
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
            every { blockedUserPort.findAllByBlockerId(receiver.id) } returns emptyList()

            then("올바른 메시지를 반환해야 한다") {
                val response = getMessageService.getMessage(messageId)
                response shouldBe MessageResponse.of(
                    message = messages[0],
                    receiver = receiver,
                    school = school,
                    isBlocked = false,
                    sender = sender
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
                    senderId = sender.id,
                    cursorId = cursorId,
                    pageable = pageRequest
                )
            } returns messages.take(10)
            every {
                messagePort.countSentMessagesAfterCursor(
                    senderId = sender.id,
                    cursorId = cursorId,
                )
            } returns 11

            val result = getMessageService.getSendMessages(cursorId)

            then("첫 10개의 발신 메시지 목록을 반환해야 한다") {
                result.messages.size shouldBe 10
                result.hasNext shouldBe true
                result shouldBe MessageListResponse.from(messages.take(10).map { message ->
                    MessageResponse.of(
                        message = message,
                        receiver = receiver,
                        school = school,
                        isBlocked = false,
                        sender = sender
                    )
                }, hasNext = true)
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
                    senderId = sender.id,
                    cursorId = cursorId,
                    pageable = pageRequest
                )
            } returns messages.drop(10)
            every {
                messagePort.countSentMessagesAfterCursor(
                    sender.id,
                    cursorId
                )
            } returns 1

            val result = getMessageService.getSendMessages(cursorId)

            then("다음 페이지의 발신 메시지 목록을 반환해야 한다") {
                result.messages.size shouldBe 1
                result.hasNext shouldBe false
                result shouldBe MessageListResponse.from(messages.drop(10).map { message ->
                    MessageResponse.of(
                        message = message,
                        receiver = receiver,
                        school = school,
                        isBlocked = false,
                        sender = sender
                    )
                }, hasNext = false)
            }
        }

        `when`("첫 페이지의 수신 메시지를 조회하면") {
            val messages = initializeMessages()
            val cursorId = Long.MAX_VALUE
            val pageRequest = PageRequest.of(0, 10, Sort.by("id").descending())
            val blockedUserIds = listOf<Long>()

            every { SecurityUtils.getLoginUser(userPort) } returns receiver
            every { userPort.findById(receiver.id) } returns receiver
            every { userPort.findById(sender.id) } returns sender
            every { schoolPort.findById(receiver.schoolId) } returns school
            every { userPort.findByIdIn(listOf(sender.id, blockedSender.id)) } returns listOf(sender, blockedSender)
            every {
                messagePort.findAllMessagesByTypeAndReceiverAfterCursor(
                    receiverId = receiver.id,
                    cursorId = cursorId,
                    blockedUserIds = blockedUserIds,
                    blockedMessageIds = blockedUserIds,
                    pageable = pageRequest
                )
            } returns messages.take(10)
            every {
                messagePort.countReceivedMessagesAfterCursor(
                    receiverId = receiver.id,
                    cursorId = cursorId,
                    blockedMessageIds = blockedUserIds
                )
            } returns 11
            every { blockedUserPort.findAllByBlockerId(receiver.id) } returns emptyList()

            val result = getMessageService.getReceivedMessages(cursorId)

            then("첫 10개의 수신 메시지 목록을 반환해야 한다") {
                result.messages.size shouldBe 10
                result.hasNext shouldBe true

                MessageListResponse.from(result.messages.take(6), result.hasNext) shouldBe
                    MessageListResponse.from(
                        messages.take(6).map { message ->
                            MessageResponse.of(
                                message = message,
                                receiver = receiver,
                                school = school,
                                isBlocked = false,
                                sender = sender
                            )
                        }, hasNext = true
                    )
            }
        }

        `when`("커서 기반으로 수신 메시지를 조회하면") {
            val messages = initializeMessages()
            val cursorId = messages[9].id
            val pageRequest = PageRequest.of(0, 10, Sort.by("id").descending())
            val blockedUserIds = listOf<Long>()

            every { SecurityUtils.getLoginUser(userPort) } returns receiver
            every { userPort.findById(receiver.id) } returns receiver
            every { userPort.findById(sender.id) } returns sender
            every { schoolPort.findById(receiver.schoolId) } returns school
            every { blockedUserPort.findAllByBlockerId(receiver.id) } returns emptyList()
            every { userPort.findByIdIn(listOf(blockedSender.id)) } returns listOf(blockedSender)
            every {
                messagePort.findAllMessagesByTypeAndReceiverAfterCursor(
                    receiverId = receiver.id,
                    cursorId = cursorId,
                    blockedUserIds = blockedUserIds,
                    blockedMessageIds = blockedUserIds,
                    pageable = pageRequest
                )
            } returns messages.drop(10)
            every {
                messagePort.countReceivedMessagesAfterCursor(
                    receiverId = receiver.id,
                    cursorId = cursorId,
                    blockedMessageIds = blockedUserIds
                )
            } returns 1

            val result = getMessageService.getReceivedMessages(cursorId)

            then("다음 페이지의 수신 메시지 목록을 반환해야 한다") {
                result.messages.size shouldBe 1
                result.hasNext shouldBe false
                result shouldBe MessageListResponse.from(messages.drop(10).map { message ->
                    MessageResponse.of(
                        message = message,
                        receiver = receiver,
                        school = school,
                        isBlocked = false,
                        sender = blockedSender
                    )
                }, hasNext = false)
            }
        }

        `when`("차단된 유저가 있는 경우 수신 메시지를 조회하면") {
            val messages = initializeMessages()
            val cursorId = Long.MAX_VALUE
            val pageRequest = PageRequest.of(0, 10, Sort.by("id").descending())
            val blockedUserIds = listOf(blockedSender.id)

            every { SecurityUtils.getLoginUser(userPort) } returns receiver
            every { userPort.findById(sender.id) } returns receiver
            every { userPort.findById(receiver.id) } returns receiver
            every { userPort.findById(blockedSender.id) } returns blockedSender
            every { userPort.findByIdIn(listOf(sender.id)) } returns listOf(sender)
            every { schoolPort.findById(receiver.schoolId) } returns school
            every { messagePort.countReceivedMessagesAfterCursor(any(), any(), any()) } returns 5
            every { blockedUserPort.findAllByBlockerId(receiver.id) } returns listOf(
                BlockedUserFixture.createWithIdAndBlockedIdAndBlockerId(
                    1,
                    receiver.id,
                    blockedSender.id,
                    1
                )
            )

            every {
                messagePort.findAllMessagesByTypeAndReceiverAfterCursor(
                    receiver.id,
                    any(),
                    any(),
                    any(),
                    pageRequest
                )
            } returns messages.filter { message ->
                !blockedUserIds.contains(message.senderId)
            }

            val result = getMessageService.getReceivedMessages(cursorId)

            then("차단된 유저의 메시지는 조회되지 않아야 한다") {
                result.messages.size shouldBe 6  // 차단된 유저의 메시지를 제외한 수신 메시지 수
                result.hasNext shouldBe false
            }
        }

        `when`("status() 메서드를 호출할 때") {
            every { SecurityUtils.getLoginUser(userPort) } returns sender
            every { messagePort.sendMessageCount(sender.id) } returns 0
            every {
                messagePort.countUnreadMessagesByReceiverIdAndBetweenSendTimeAndMessageOpenTime(
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns 0
            every { blockedUserPort.findAllByBlockerId(sender.id) } returns emptyList()

            then("SendMessageStatusResponse를 반환해야 한다") {
                val response = getMessageService.status()
                response shouldBe SendMessageStatusResponse(
                    isSendAllowed = true,
                    countRemainingMessages = 3,
                    countUnReadMessages = 0
                )
            }

            then("메시지 전송 한도를 검증해야 한다") {
                getMessageService.status()
                verify { messagePort.sendMessageCount(sender.id) }
            }
        }

        `when`("차단된 메시지 목록을 조회하면") {
            val messages = initializeMessages()
            val cursorId = Long.MAX_VALUE
            val pageRequest = PageRequest.of(0, 10, Sort.by("id").descending())
            val blockedUserIds = listOf(blockedSender.id)

            every { SecurityUtils.getLoginUser(userPort) } returns receiver
            every { userPort.findById(receiver.id) } returns receiver
            every { userPort.findById(blockedSender.id) } returns blockedSender
            every { schoolPort.findById(receiver.schoolId) } returns school
            every { messagePort.findById(1) } returns messages[0]

            every { blockedUserPort.findAllByBlockerId(receiver.id) } returns listOf(
                BlockedUserFixture.createWithIdAndBlockedIdAndBlockerId(1, receiver.id, blockedSender.id, 1),
            )

            every {
                blockedUserPort.findAllByBlockerIdAfterCursor(
                    blockerId = receiver.id,
                    cursorId = cursorId,
                    pageable = pageRequest
                )
            } returns listOf(
                BlockedUserFixture.createWithIdAndBlockedIdAndBlockerId(
                    1,
                    receiver.id,
                    blockedSender.id,
                    1
                )
            )

            every {
                blockedUserPort.countBlockedUsersAfterCursor(
                    blockerId = receiver.id,
                    cursorId = cursorId,
                    pageable = pageRequest
                )
            } returns 1

            messages.filter { blockedUserIds.contains(it.senderId) }.forEach { message ->
                every { messagePort.findById(message.id) } returns message
            }

            val result = getMessageService.getBlockedMessages(cursorId)

            then("차단된 메시지 목록을 반환해야 한다") {
                result.messages.size shouldBe 1  // 차단된 유저의 메시지 수
                result.hasNext shouldBe false
            }
        }

    }
})

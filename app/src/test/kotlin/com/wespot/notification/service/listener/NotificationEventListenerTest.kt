package com.wespot.notification.service.listener

import com.wespot.common.service.ServiceTest
import com.wespot.message.Message
import com.wespot.message.MessageTimeValidator
import com.wespot.message.event.ReceivedMessageEvent
import com.wespot.message.port.out.MessagePort
import com.wespot.notification.NotificationType
import com.wespot.notification.port.out.NotificationPort
import com.wespot.user.event.SignUpUserEvent
import com.wespot.user.fixture.UserFixture
import com.wespot.user.port.out.UserPort
import com.wespot.vote.event.EndVoteEvent
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

class NotificationEventListenerTest @Autowired constructor(
    private val eventPublisher: ApplicationEventPublisher,
    private val userPort: UserPort,
    private val messagePort: MessagePort,
    private val notificationPort: NotificationPort,
) : ServiceTest() {

    @Test
    fun `수신자가 쪽지를 받았을 때, 알림이 발생한다`() {
        // given
        val fixedClock = Clock.fixed(Instant.parse("2023-03-18T18:00:00Z"), ZoneId.of("UTC"))
        MessageTimeValidator.setClock(fixedClock)

        val sender = userPort.save(UserFixture.createWithIdAndEmail(0, "Test1@KAKAO"))
        UserFixture.setSecurityContextUser(sender)
        val receiver = userPort.save(UserFixture.createWithIdAndEmail(0, "Test2@KAKAO"))
        val message = messagePort.save(
            Message.sendMessage(
                content = "content",
                receiver = receiver,
                sender = sender,
                senderName = sender.name,
                isAnonymous = false
            )
        )

        // when
        eventPublisher.publishEvent(
            ReceivedMessageEvent(
                receiver = receiver,
                messageId = message.id
            )
        )
        val notifications = notificationPort.findAll()

        // then
        notifications.size shouldBe 1
        notifications[0].userId shouldBe receiver.id
        notifications[0].type shouldBe NotificationType.MESSAGE_RECEIVED
        notifications[0].targetId shouldBe message.id

        clearAllMocks()
        MessageTimeValidator.resetClock()
    }

    @Test
    fun `유저가 회원가입했을 때, 알림이 발생한다`() {
        // given
        val users = listOf(
            userPort.save(UserFixture.createWithIdAndEmail(0, "Test1@KAKAO")),
            userPort.save(UserFixture.createWithIdAndEmail(0, "Test2@KAKAO")),
            userPort.save(UserFixture.createWithIdAndEmail(0, "Test3@KAKAO")),
            userPort.save(UserFixture.createWithIdAndEmail(0, "Test4@KAKAO")),
            userPort.save(UserFixture.createWithIdAndEmail(0, "Test5@KAKAO"))
        )

        // when
        eventPublisher.publishEvent(SignUpUserEvent(users[0]))
        val notifications = notificationPort.findAll()

        // then
        notifications.size shouldBe 4
        notifications[0].userId shouldBe users[1].id
        notifications[1].userId shouldBe users[2].id
        notifications[2].userId shouldBe users[3].id
        notifications[3].userId shouldBe users[4].id
    }

    @Test
    fun `투표 종료가 되었을 때, 알림이 발생한다`() {
        // given
        val users = listOf(
            userPort.save(UserFixture.createWithIdAndEmail(0, "Test1@KAKAO")),
            userPort.save(UserFixture.createWithIdAndEmail(0, "Test2@KAKAO")),
            userPort.save(UserFixture.createWithIdAndEmail(0, "Test3@KAKAO")),
            userPort.save(UserFixture.createWithIdAndEmail(0, "Test4@KAKAO")),
            userPort.save(UserFixture.createWithIdAndEmail(0, "Test5@KAKAO"))
        )

        // when
        eventPublisher.publishEvent(EndVoteEvent())
        val notifications = notificationPort.findAll()

        // then
        notifications.size shouldBe 5
        notifications[0].userId shouldBe users[0].id
        notifications[1].userId shouldBe users[1].id
        notifications[2].userId shouldBe users[2].id
        notifications[3].userId shouldBe users[3].id
        notifications[4].userId shouldBe users[4].id
        notifications[0].type shouldBe NotificationType.VOTE_RESULT
    }

}

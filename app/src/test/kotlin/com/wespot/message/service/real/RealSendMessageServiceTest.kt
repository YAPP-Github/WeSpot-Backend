package com.wespot.message.service.real

import com.wespot.common.service.ServiceTest
import com.wespot.message.Message
import com.wespot.message.MessageTimeValidator
import com.wespot.message.event.MessageLimitEvent
import com.wespot.message.port.out.MessagePort
import com.wespot.notification.Notification
import com.wespot.notification.NotificationType
import com.wespot.notification.port.out.NotificationPort
import com.wespot.user.fixture.UserFixture
import com.wespot.user.port.out.UserPort
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

class RealSendMessageServiceTest @Autowired constructor(
    private val userPort: UserPort,
    private val messagePort: MessagePort,
    private val notificationPort: NotificationPort,
    private val eventPublisher: ApplicationEventPublisher
) : ServiceTest() {

    @Test
    fun `쪽지를 3개 보냈을 때, 쪽지 독려 알림이 비활성화된다`() {
        // given
        val fixedClock = Clock.fixed(Instant.parse("2023-03-18T18:00:00Z"), ZoneId.of("UTC"))
        MessageTimeValidator.setClock(fixedClock)

        val sender = userPort.save(UserFixture.createWithIdAndEmail(0, "Test1@KAKAO"))
        val receiver = userPort.save(UserFixture.createWithIdAndEmail(0, "Test2@KAKAO"))
        UserFixture.setSecurityContextUser(sender)
        val message = messagePort.save(
            Message.sendMessage(
                content = "content",
                receiver = receiver,
                sender = sender,
                senderName = "senderName",
                isAnonymous = false
            )
        )
        notificationPort.save(
            Notification.createMessageInitialState(
                userId = sender.id,
                type = NotificationType.MESSAGE,
                targetId = 0,
                title = "ㅎㅇ",
                body = "ㅎㅇ",
            )
        )

        // when
        eventPublisher.publishEvent(
            MessageLimitEvent(
                sender.id,
                3
            )
        )
        val notifications = notificationPort.findAll()

        // then
        notifications.size shouldBe 1
        notifications[0].isEnabled shouldBe false

        clearAllMocks()
        MessageTimeValidator.resetClock()
    }

}

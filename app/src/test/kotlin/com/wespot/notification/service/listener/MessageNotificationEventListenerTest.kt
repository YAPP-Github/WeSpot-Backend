package com.wespot.notification.service.listener

import com.wespot.common.service.ServiceTest
import com.wespot.firebase.FirebaseNotificationService
import com.wespot.message.event.ReadMessageByReceiverEvent
import com.wespot.message.event.ReceivedMessageEvent
import com.wespot.message.fixture.MessageFixture
import com.wespot.message.port.out.MessagePort
import com.wespot.notification.NotificationType
import com.wespot.notification.port.out.NotificationPort
import com.wespot.user.fixture.UserFixture
import com.wespot.user.port.out.UserPort
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.awaitility.kotlin.await
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.concurrent.TimeUnit

class MessageNotificationEventListenerTest @Autowired constructor(
    private val messageNotificationEventListener: MessageNotificationEventListener,
    private val userPort: UserPort,
    private val messagePort: MessagePort,
    private val notificationPort: NotificationPort,
) : ServiceTest() {

    @Test
    fun `쪽지를 3개를 보내 알림이 비활성화된다`() {
//        // given
//        val sender = userPort.save(UserFixture.createWithId(0))
//        val receiver = userPort.save(UserFixture.createWithId(0))
//        val message = messagePort.save(MessageFixture.createWithIdAndSenderIdAndReceiverId(0, sender.id, receiver.id))
//        val notification = notificationPort.save(
//            NotificationFixture.createWithIdAndUserIdAndTypeAndTargetId(
//                0,
//                sender.id,
//                NotificationType.MESSAGE,
//                message.id
//            )
//        )
//
//        // when
//        messageNotificationEventListener.disableMessageNotificationByLimit(MessageLimitEvent(sender.id, 3))
//
//        // then
//        await.atMost(2, TimeUnit.SECONDS).untilAsserted {
//            val disableNotification = notificationPort.findById(notification.id)
//            disableNotification!!.isEnabled shouldBe false
//        }
    }

    @Test
    fun `쪽지를 받은 이에게 알림이 발송된다`() {
        // given
        val sender = userPort.save(UserFixture.createWithIdSchool(0))
        val receiver = userPort.save(UserFixture.createWithIdSchool(0))
        val message = messagePort.save(MessageFixture.createWithIdAndSenderIdAndReceiverId(0, sender.id, receiver.id))
        val sendService = mockk<FirebaseNotificationService>()

        // when
        every { sendService.sendNotification(any(), any()) } returns Unit
        messageNotificationEventListener.receiveMessage(ReceivedMessageEvent(receiver, message.id))

        // then
        await.atMost(2, TimeUnit.SECONDS).untilAsserted {
            val notifications = notificationPort.findAll()
            notifications.size shouldBe 1
            notifications[0].userId shouldBe receiver.id
            notifications[0].type shouldBe NotificationType.MESSAGE_RECEIVED
        }
    }

    @Test
    fun `수신자가 쪽지를 읽으면 송신자에게 알림이 발송된다`() {
        // given
        val sender = userPort.save(UserFixture.createWithIdSchool(0))
        val receiver = userPort.save(UserFixture.createWithIdSchool(0))
        val message = messagePort.save(MessageFixture.createWithIdAndSenderIdAndReceiverId(0, sender.id, receiver.id))
        val sendService = mockk<FirebaseNotificationService>()

        // when
        every { sendService.sendNotification(any(), any()) } returns Unit
        messageNotificationEventListener.readMessageByReceiver(
            ReadMessageByReceiverEvent(
                sender,
                receiver,
                message.id,
                false
            )
        )

        // then
        await.atMost(2, TimeUnit.SECONDS).untilAsserted {
            val notifications = notificationPort.findAll()
            notifications.size shouldBe 1
            notifications[0].userId shouldBe sender.id
            notifications[0].type shouldBe NotificationType.MESSAGE_SENT
            notifications[0].targetId shouldBe message.id
        }
    }

}

package com.wespot.notification.service

import com.wespot.DatabaseCleanup
import com.wespot.notification.Notification
import com.wespot.notification.NotificationType
import com.wespot.user.fixture.UserFixture
import io.kotest.assertions.throwables.shouldNotThrow
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
class NotificationServiceHelperTest @Autowired constructor(
    private val notificationServiceHelper: NotificationServiceHelper,
    private val databaseCleanup: DatabaseCleanup
) { // TODO : Mocking 고수 기천짱 나를 도와줘..

    @AfterEach
    fun tearDown() {
        databaseCleanup.execute()
    }

    @Test
    fun `쪽지 알림 여러개를 한번에 발송한다`() {
        // given
        val users = (1..5).map { UserFixture.createWithId(it.toLong()) }
        val notifications = (1..5).map {
            Notification.createMessageInitialState(
                userId = users[it - 1].id,
                title = "title",
                body = "body",
                targetId = 1,
                type = NotificationType.MESSAGE
            )
        }
        val sendService = mockk<NotificationSendService>()

        // when
        every { sendService.sendMulticastNotification(any(), any()) } returns Unit
        shouldNotThrow<IllegalArgumentException> {
            notificationServiceHelper.sendMulticastNotification(
                users,
                notifications
            )
        }

        // then
//        verify {
//            sendService.sendMulticastNotification(
//                users,
//                NotificationInfo.createMessageInitialState(
//                    title = notifications[0].content,
//                    body = "아직 미정",
//                    targetId = notifications[0].targetId,
//                    type = notifications[0].type
//                )
//            )
//        }
    }

    @Test
    fun `투표 알림 여러개를 한번에 발송한다`() {
        // given
        val users = (1..5).map { UserFixture.createWithId(it.toLong()) }
        val notifications = (1..5).map {
            Notification.createVoteInitialState(
                userId = users[it - 1].id,
                title = "title",
                body = "body",
                date = LocalDate.now(),
                type = NotificationType.VOTE
            )
        }
        val sendService = mockk<NotificationSendService>()

        // when
        every { sendService.sendMulticastNotification(any(), any()) } returns Unit
        shouldNotThrow<IllegalArgumentException> {
            notificationServiceHelper.sendMulticastNotification(
                users,
                notifications
            )
        }

        // then
//        verify(exactly = 1) {
//            sendService.sendMulticastNotification(
//                users,
//                NotificationInfo.createVoteInitialState(
//                    title = notifications[0].content,
//                    body = "아직 미정",
//                    date = notifications[0].date,
//                    type = notifications[0].type
//                )
//            )
//        }
    }

    @Test
    fun `쪽지 알림을 발송한다`() {
        // given
        val user = UserFixture.createWithId(1)
        val notification = Notification.createMessageInitialState(
            userId = user.id,
            title = "title",
            body = "body",
            targetId = 1,
            type = NotificationType.MESSAGE
        )
        val sendService = mockk<NotificationSendService>()

        // when
        every { sendService.sendNotification(any(), any()) } returns Unit
        shouldNotThrow<IllegalArgumentException> { notificationServiceHelper.sendNotification(user, notification) }

        // then
//        verify {
//            sendService.sendNotification(
//                user,
//                NotificationInfo.createMessageInitialState(
//                    title = notification.content,
//                    body = "아직 미정",
//                    targetId = 1,
//                    type = notification.type
//                )
//            )
//        }
    }

    @Test
    fun `투표 알림을 발송한다`() {
        // given
        val user = UserFixture.createWithId(1)
        val notification = Notification.createVoteInitialState(
            userId = user.id,
            title = "title",
            body = "body",
            date = LocalDate.now(),
            type = NotificationType.VOTE
        )
        val sendService = mockk<NotificationSendService>()

        // when
        every { sendService.sendNotification(any(), any()) } returns Unit
        shouldNotThrow<IllegalArgumentException> { notificationServiceHelper.sendNotification(user, notification) }

        // then
//        verify(exactly = 1) {
//            sendService.sendNotification(
//                user,
//                NotificationInfo.createVoteInitialState(
//                    title = notification.content,
//                    body = "아직 미정",
//                    date = notification.date,
//                    type = notification.type
//                )
//            )
//        }
    }

}

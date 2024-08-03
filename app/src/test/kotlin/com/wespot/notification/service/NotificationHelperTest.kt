package com.wespot.notification.service

import com.wespot.DatabaseCleanup
import com.wespot.firebase.FirebaseNotificationService
import com.wespot.notification.Notification
import com.wespot.notification.NotificationInfo
import com.wespot.notification.NotificationType
import com.wespot.user.fixture.UserFixture
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
class NotificationHelperTest @Autowired constructor(
    private val databaseCleanup: DatabaseCleanup
) {

    private val notificationSendService = mockk<FirebaseNotificationService>()
    private val notificationHelper = NotificationHelper(notificationSendService)

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

        // when
        every { notificationSendService.sendMulticastNotification(any(), any()) } returns Unit
        notificationHelper.sendNotifications(users, notifications)

        // then
        verify {
            notificationSendService.sendMulticastNotification(
                users,
                NotificationInfo.createInitialState(notifications[0])
            )
        }
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

        // when
        every { notificationSendService.sendMulticastNotification(any(), any()) } returns Unit
        notificationHelper.sendNotifications(users, notifications)

        // then
        verify {
            notificationSendService.sendMulticastNotification(
                users,
                NotificationInfo.createInitialState(notifications[0])
            )
        }
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

        // when
        every { notificationSendService.sendNotification(any(), any()) } returns Unit
        notificationHelper.sendNotification(user, notification)

        // then
        verify { notificationSendService.sendNotification(user, NotificationInfo.createInitialState(notification)) }
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

        // when
        every { notificationSendService.sendNotification(any(), any()) } returns Unit
        notificationHelper.sendNotification(user, notification)

        // then
        verify { notificationSendService.sendNotification(user,NotificationInfo.createInitialState(notification)) }
    }

}

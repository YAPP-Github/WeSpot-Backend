package com.wespot.notification.service

import com.wespot.common.service.ServiceTest
import com.wespot.notification.NotificationType
import com.wespot.notification.fixtrue.NotificationFixture
import com.wespot.notification.port.out.NotificationPort
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

class DisabledNotificationServiceTest @Autowired constructor(
    private val notificationPort: NotificationPort,
    private val disabledNotificationService: DisabledNotificationService,
) : ServiceTest() {

    @Test
    fun `투표 관련 알림을 비활성화한다`() {
        // given
        val today = LocalDate.now()
        notificationPort.save(
            NotificationFixture.createWithTypeAndCreatedAt(
                NotificationType.VOTE,
                today.minusDays(1).atStartOfDay()
            )
        )
        notificationPort.save(
            NotificationFixture.createWithTypeAndCreatedAt(
                NotificationType.MESSAGE,
                today.atStartOfDay()
            )
        )

        // when
        disabledNotificationService.disableVoteNotifications(today)
        val notifications = notificationPort.findAll()

        // then
        notifications.size shouldBe 2
        notifications[0].type shouldBe NotificationType.VOTE
        notifications[0].isEnabled shouldBe false
    }

    @Test
    fun `쪽지 관련 알림을 비활성화한다`() {
        // given
        val today = LocalDate.now()
        notificationPort.save(
            NotificationFixture.createWithTypeAndCreatedAt(
                NotificationType.VOTE,
                today.minusDays(1).atStartOfDay()
            )
        )
        notificationPort.save(
            NotificationFixture.createWithTypeAndCreatedAt(
                NotificationType.MESSAGE,
                today.atStartOfDay()
            )
        )

        // when
        disabledNotificationService.disableMessageNotifications(today)
        val notifications = notificationPort.findAll()

        // then
        notifications.size shouldBe 2
        notifications[1].type shouldBe NotificationType.MESSAGE
        notifications[1].isEnabled shouldBe false
    }

}

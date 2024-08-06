package com.wespot.notification.service

import com.wespot.DatabaseCleanup
import com.wespot.common.service.ServiceTest
import com.wespot.notification.NotificationType
import com.wespot.notification.fixtrue.NotificationFixture
import com.wespot.notification.port.out.NotificationPort
import com.wespot.user.fixture.UserFixture
import com.wespot.user.port.out.UserPort
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class InquiryNotificationServiceTest @Autowired constructor(
    private val inquiryNotificationService: InquiryNotificationService,
    private val userPort: UserPort,
    private val notificationPort: NotificationPort,
) : ServiceTest() {

    @Test
    fun `유저가 알림을 조회한다`() {
        // given
        val user = userPort.save(UserFixture.createWithId(0))
        UserFixture.setSecurityContextUser(user)
        val notifications = notificationPort.saveAll((user.id..user.id + 4).map {
            NotificationFixture.createWithIdAndUserIdAndTypeAndTargetId(
                0,
                it,
                NotificationType.VOTE,
                1
            )
        })
        val savedNotification = notificationPort.save(
            NotificationFixture.createWithIdAndUserIdAndTypeAndTargetId(
                0,
                user.id,
                NotificationType.VOTE,
                1
            )
        )

        // when
        val responses = inquiryNotificationService.getNotifications().notifications

        // then
        responses.size shouldBe 2
        responses[1].id shouldBe notifications[0].id
        responses[0].id shouldBe savedNotification.id
    }

    @Test
    fun `유저가 알림을 조회해, 읽음 상태로 변경된다`() {
        // given
        val user = userPort.save(UserFixture.createWithId(0))
        UserFixture.setSecurityContextUser(user)
        notificationPort.saveAll((user.id..user.id + 4).map {
            NotificationFixture.createWithIdAndUserIdAndTypeAndTargetId(
                0,
                it,
                NotificationType.VOTE,
                1
            )
        })
        notificationPort.save(
            NotificationFixture.createWithIdAndUserIdAndTypeAndTargetId(
                0,
                user.id,
                NotificationType.VOTE,
                1
            )
        )

        // when
        val responses = inquiryNotificationService.getNotifications().notifications
        inquiryNotificationService.readNotification(responses[0].id)
        val actual = inquiryNotificationService.getNotifications().notifications

        // then
        actual.size shouldBe 2
        actual[0].isNew shouldBe false
        actual[1].isNew shouldBe true
    }


}

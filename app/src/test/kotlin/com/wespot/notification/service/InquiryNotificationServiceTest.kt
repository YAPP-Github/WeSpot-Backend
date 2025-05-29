package com.wespot.notification.service

import com.wespot.common.service.ServiceTest
import com.wespot.notification.NotificationType
import com.wespot.notification.fixtrue.NotificationFixture
import com.wespot.notification.port.out.NotificationPort
import com.wespot.school.SchoolJpaRepository
import com.wespot.school.fixture.SchoolFixture
import com.wespot.user.fixture.UserFixture
import com.wespot.user.port.out.UserPort
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate

class InquiryNotificationServiceTest @Autowired constructor(
    private val inquiryNotificationService: InquiryNotificationService,
    private val userPort: UserPort,
    private val notificationPort: NotificationPort,
    private val schoolJpaRepository: SchoolJpaRepository,
) : ServiceTest() {

    @Test
    fun `유저가 알림을 조회한다`() {
        // given
        val school = schoolJpaRepository.save(SchoolFixture.generateJpaEntity())
        val user = userPort.save(UserFixture.createWithId(0, schoolJpaEntity = school))
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
        val responses1 = inquiryNotificationService.getNotifications(null, 1)
        val responses2 = inquiryNotificationService.getNotifications(responses1.notifications[0].id, 1)

        // then
        responses1.notifications.size shouldBe 1
        responses1.notifications[0].id shouldBe savedNotification.id
        responses1.notifications[0].date shouldBe LocalDate.now().toString()
        responses1.lastCursorId shouldBe savedNotification.id
        responses1.hasNext shouldBe true
        responses2.notifications.size shouldBe 1
        responses2.notifications[0].id shouldBe notifications[0].id
        responses2.notifications[0].date shouldBe LocalDate.now().toString()
        responses2.lastCursorId shouldBe notifications[0].id
        responses2.hasNext shouldBe false
    }

    @Test
    fun `유저가 알림을 조회해, 읽음 상태로 변경된다`() {
        // given
        val school = schoolJpaRepository.save(SchoolFixture.generateJpaEntity())
        val user = userPort.save(UserFixture.createWithId(id = 0, schoolJpaEntity = school))
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
        val responses = inquiryNotificationService.getNotifications(null, 100).notifications
        inquiryNotificationService.readNotification(responses[0].id)
        val actual = inquiryNotificationService.getNotifications(null, 100).notifications

        // then
        actual.size shouldBe 2
        actual[0].isNew shouldBe false
        actual[0].date shouldBe LocalDate.now().toString()
        actual[1].isNew shouldBe true
        actual[1].date shouldBe LocalDate.now().toString()
    }


}

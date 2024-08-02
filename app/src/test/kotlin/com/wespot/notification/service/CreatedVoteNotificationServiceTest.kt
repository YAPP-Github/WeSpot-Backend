package com.wespot.notification.service

import com.wespot.DatabaseCleanup
import com.wespot.notification.NotificationType
import com.wespot.notification.port.out.NotificationPort
import com.wespot.user.fixture.UserFixture
import com.wespot.user.port.out.UserPort
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class CreatedVoteNotificationServiceTest @Autowired constructor(
    private val createdVoteNotificationService: CreatedVoteNotificationService,
    private val notificationPort: NotificationPort,
    private val userPort: UserPort,
    private val databaseCleanup: DatabaseCleanup
) {

    @AfterEach
    fun tearDown() {
        databaseCleanup.execute()
    }

    @Test
    fun `투표 생성 알림을 발송한다`() {
        // given
        (1..5).map { UserFixture.createWithId(0) }.forEach { userPort.save(it) }
        val sendService = mockk<NotificationSendService>()

        // when
        every { sendService.sendNotification(any(), any()) } returns Unit
        createdVoteNotificationService.encourageVote()
        val notifications = notificationPort.findAll()

        // then
        notifications.size shouldBe 5
        notifications[0].type shouldBe NotificationType.VOTE
        notifications[0].isEnabled shouldBe true
        notifications[0].targetId shouldBe 0
    }
}

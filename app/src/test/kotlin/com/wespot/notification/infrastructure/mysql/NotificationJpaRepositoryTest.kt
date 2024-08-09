package com.wespot.notification.infrastructure.mysql

import com.wespot.notification.NotificationJpaEntity
import com.wespot.notification.NotificationJpaRepository
import com.wespot.notification.NotificationMapper
import com.wespot.notification.NotificationType
import com.wespot.notification.fixtrue.NotificationFixture
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class NotificationJpaRepositoryTest @Autowired constructor(
    private val notificationJpaRepository: NotificationJpaRepository
) {

    @Test
    fun `알림 목록 조회에 Cursor Based Pagination을 도입한다`() {
        // given
        val userId = 1L
        val notification1 = createSavedNotificationBy(userId)
        createSavedNotificationBy(2)
        val notification2 = createSavedNotificationBy(userId)
        createSavedNotificationBy(3)
        val notification3 = createSavedNotificationBy(userId)
        createSavedNotificationBy(4)
        val notification4 = createSavedNotificationBy(userId)
        createSavedNotificationBy(5)
        val notification5 = createSavedNotificationBy(userId)

        // when
        val firstSearch =
            notificationJpaRepository.findAllByUserIdOrderByBaseEntityCreatedAtDesc(userId, Long.MAX_VALUE, 3)
        val secondSearch =
            notificationJpaRepository.findAllByUserIdOrderByBaseEntityCreatedAtDesc(userId, firstSearch[2].id, 4)

        // then
        firstSearch.size shouldBe 3
        firstSearch[0] shouldBe notification5
        firstSearch[1] shouldBe notification4
        firstSearch[2] shouldBe notification3
        secondSearch.size shouldBe 2
        secondSearch[0] shouldBe notification2
        secondSearch[1] shouldBe notification1
    }

    private fun createSavedNotificationBy(userId: Long): NotificationJpaEntity {
        val notification = NotificationFixture.createWithUserIdAndType(userId, NotificationType.MESSAGE)
        val notificationJpaEntity = NotificationMapper.mapToJpaEntity(notification)

        return notificationJpaRepository.save(notificationJpaEntity)
    }

}

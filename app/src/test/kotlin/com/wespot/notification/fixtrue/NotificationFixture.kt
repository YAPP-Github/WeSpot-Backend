package com.wespot.notification.fixtrue

import com.wespot.notification.Notification
import com.wespot.notification.NotificationType
import java.time.LocalDate
import java.time.LocalDateTime

object NotificationFixture {

    fun createWithType(notificationType: NotificationType) = Notification(
        id = 0L,
        userId = 1L,
        type = notificationType,
        date = LocalDate.now(),
        targetId = 1L,
        title = "title",
        body = "body",
        isRead = false,
        readAt = LocalDateTime.now(),
        isEnabled = true,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now()
    )

    fun createWithUserIdAndType(userId: Long, notificationType: NotificationType) = Notification(
        id = 0L,
        userId = userId,
        type = notificationType,
        date = LocalDate.now(),
        targetId = 1L,
        title = "title",
        body = "body",
        isRead = false,
        readAt = LocalDateTime.now(),
        isEnabled = true,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now()
    )


    fun createWithTypeAndCreatedAt(notificationType: NotificationType, createdAt: LocalDateTime) = Notification(
        id = 0L,
        userId = 1L,
        type = notificationType,
        date = LocalDate.now(),
        targetId = 1L,
        title = "title",
        body = "body",
        isRead = false,
        readAt = LocalDateTime.now(),
        isEnabled = true,
        createdAt = createdAt,
        updatedAt = createdAt
    )

    fun createWithIdAndType(id: Long, notificationType: NotificationType) = Notification(
        id = id,
        userId = 1L,
        type = notificationType,
        date = LocalDate.now(),
        targetId = 1L,
        title = "title",
        body = "body",
        isRead = false,
        readAt = LocalDateTime.now(),
        isEnabled = true,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now()
    )

    fun createWithIdAndUserIdAndTypeAndTargetId(
        id: Long,
        userId: Long,
        notificationType: NotificationType,
        targetId: Long
    ) = Notification(
        id = id,
        userId = userId,
        type = notificationType,
        date = LocalDate.now(),
        targetId = targetId,
        title = "title",
        body = "body",
        isRead = false,
        readAt = LocalDateTime.now(),
        isEnabled = true,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now()
    )

}

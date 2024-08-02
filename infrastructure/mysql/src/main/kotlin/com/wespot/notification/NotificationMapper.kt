package com.wespot.notification

import com.wespot.common.BaseEntity

object NotificationMapper {

    fun mapToJpaEntity(notification: Notification): NotificationJpaEntity {
        return NotificationJpaEntity(
            id = notification.id,
            userId = notification.userId,
            type = notification.type,
            date = notification.date,
            targetId = notification.targetId,
            title = notification.title,
            body = notification.body,
            isRead = notification.isRead,
            readAt = notification.readAt,
            isEnabled = notification.isEnabled,
            baseEntity = BaseEntity(notification.createdAt, notification.updatedAt)
        )
    }

    fun mapToDomainEntity(notificationJpaEntity: NotificationJpaEntity): Notification {
        return Notification(
            id = notificationJpaEntity.id,
            userId = notificationJpaEntity.userId,
            type = notificationJpaEntity.type,
            date = notificationJpaEntity.date,
            targetId = notificationJpaEntity.targetId,
            title = notificationJpaEntity.title,
            body= notificationJpaEntity.body,
            isRead = notificationJpaEntity.isRead,
            readAt = notificationJpaEntity.readAt,
            isEnabled = notificationJpaEntity.isEnabled,
            createdAt = notificationJpaEntity.baseEntity.createdAt,
            updatedAt = notificationJpaEntity.baseEntity.updatedAt
        )
    }

}

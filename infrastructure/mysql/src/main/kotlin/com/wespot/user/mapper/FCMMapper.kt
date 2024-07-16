package com.wespot.user.mapper

import com.wespot.common.BaseEntity
import com.wespot.user.FCM
import com.wespot.user.entity.FCMJpaEntity

object FCMMapper {
    fun mapToDomainEntity(fcmJpaEntity: FCMJpaEntity): FCM =
        FCM(
            id = fcmJpaEntity.id,
            fcmToken = fcmJpaEntity.fcmToken,
            createdAt = fcmJpaEntity.createdAt
        )

    fun mapToJpaEntity(fcm: FCM?): FCMJpaEntity =
        FCMJpaEntity(
            id = fcm?.id ?: 0L,
            fcmToken = fcm?.fcmToken,
            createdAt = fcm?.createdAt,
        )

}

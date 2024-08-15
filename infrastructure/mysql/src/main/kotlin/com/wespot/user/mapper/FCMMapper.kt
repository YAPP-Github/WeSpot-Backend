package com.wespot.user.mapper

import com.wespot.common.BaseEntity
import com.wespot.user.FCM
import com.wespot.user.entity.FCMJpaEntity
import java.time.LocalDateTime

object FCMMapper {
    fun mapToDomainEntity(fcmJpaEntity: FCMJpaEntity): FCM =
        FCM(
            id = fcmJpaEntity.id,
            fcmToken = fcmJpaEntity.fcmToken,
            createdAt = fcmJpaEntity.baseEntity!!.createdAt
        )

    fun mapToJpaEntity(fcm: FCM?): FCMJpaEntity =
        FCMJpaEntity(
            id = fcm?.id ?: 0,
            fcmToken = fcm?.fcmToken,
            baseEntity = BaseEntity(
                createdAt = fcm?.createdAt ?: LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        )

}

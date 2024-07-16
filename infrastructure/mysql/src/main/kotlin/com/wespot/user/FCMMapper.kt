package com.wespot.user

import com.wespot.common.BaseEntity

object FCMMapper {
    fun mapToDomainEntity(fcmJpaEntity: FCMJpaEntity): FCM =
        FCM(
            id = fcmJpaEntity.id,
            fcmToken = fcmJpaEntity.fcmToken,
            createdAt = fcmJpaEntity.baseEntity.createdAt
        )

    fun mapToJpaEntity(fcm: FCM): FCMJpaEntity =
        FCMJpaEntity(
            id = fcm.id,
            fcmToken = fcm.fcmToken,
            baseEntity = BaseEntity(
                createdAt = fcm.createdAt,
                updatedAt = null
            ) // TODO : Domain 객체에 CreatedAt만 존재해서 조금 애매하네요.. 이 부분은 어떻게 해야할까요?
        )

}

package com.wespot.user.mapper

import com.wespot.user.UserVersion
import com.wespot.user.entity.UserVersionJpaEntity

object UserVersionMapper {

    fun mapToDomainEntity(userVersionJpaEntity: UserVersionJpaEntity) = UserVersion(
        id = userVersionJpaEntity.id,
        userId = userVersionJpaEntity.userId,
        iosVersionName = userVersionJpaEntity.iosVersionName,
        androidVersionName = userVersionJpaEntity.androidVersionName,
        iosVersionNameWhenSignUp = userVersionJpaEntity.iosVersionNameWhenSignUp,
        androidVersionNameWhenSignUp = userVersionJpaEntity.androidVersionNameWhenSignUp,
        createdAt = userVersionJpaEntity.baseEntity.createdAt,
        updatedAt = userVersionJpaEntity.baseEntity.updatedAt
    )

    fun mapToJpaEntity(userVersion: UserVersion) = UserVersionJpaEntity(
        id = userVersion.id,
        userId = userVersion.userId,
        iosVersionName = userVersion.iosVersionName,
        androidVersionName = userVersion.androidVersionName,
        iosVersionNameWhenSignUp = userVersion.iosVersionNameWhenSignUp,
        androidVersionNameWhenSignUp = userVersion.androidVersionNameWhenSignUp,
        baseEntity = com.wespot.common.BaseEntity(
            createdAt = userVersion.createdAt,
            updatedAt = userVersion.updatedAt
        )
    )

}

package com.wespot.user

import com.wespot.common.BaseEntity

object UserMapper {

    fun mapToDomainEntity(userJpaEntity: UserJpaEntity): User =
        User(
            id = userJpaEntity.id,
            name = userJpaEntity.name,
            introduction = userJpaEntity.introduction,
            schoolId = userJpaEntity.schoolId,
            grade = userJpaEntity.grade,
            groupNumber = userJpaEntity.groupNumber,
            setting = SettingMapper.mapToDomainEntity(userJpaEntity.setting),
            profile = ProfileMapper.mapToDomainEntity(userJpaEntity.profile),
            fcm = FCMMapper.mapToDomainEntity(userJpaEntity.fcm),
            social = SocialMapper.mapToDomainEntity(userJpaEntity.social),
            createdAt = userJpaEntity.baseEntity.createdAt,
            updatedAt = userJpaEntity.baseEntity.updatedAt,
            withdrawAt = userJpaEntity.withdrawAt
        )

    fun mapToJpaEntity(user: User): UserJpaEntity =
        UserJpaEntity(
            id = user.id,
            name = user.name,
            introduction = user.introduction,
            schoolId = user.schoolId,
            grade = user.grade,
            setting = SettingMapper.mapToJpaEntity(user.setting),
            profile = ProfileMapper.mapToJpaEntity(user.profile),
            fcm = FCMMapper.mapToJpaEntity(user.fcm),
            social = SocialMapper.mapToJpaEntity(user.social),
            groupNumber = user.groupNumber,
            withdrawAt = user.withdrawAt,
            baseEntity = BaseEntity(user.createdAt, user.updatedAt),
        )

}

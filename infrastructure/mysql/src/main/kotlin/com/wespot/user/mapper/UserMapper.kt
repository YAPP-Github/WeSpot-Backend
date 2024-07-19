package com.wespot.user.mapper

import com.wespot.common.BaseEntity
import com.wespot.user.*
import com.wespot.user.entity.*

object UserMapper {

        fun mapToDomainEntity(userJpaEntity: UserJpaEntity): User =
            User(
                id = userJpaEntity.id,
                email = userJpaEntity.email,
                password = userJpaEntity.password,
                name = userJpaEntity.name,
                introduction = userJpaEntity.introduction,
                gender = userJpaEntity.gender,
                schoolId = userJpaEntity.schoolId,
                grade = userJpaEntity.grade,
                classNumber = userJpaEntity.classNumber,
                role = userJpaEntity.role,
                setting = SettingMapper.mapToDomainEntity(userJpaEntity.setting),
                profile = ProfileMapper.mapToDomainEntity(userJpaEntity.profile),
                fcm = FCMMapper.mapToDomainEntity(userJpaEntity.fcm),
                social = SocialMapper.mapToDomainEntity(userJpaEntity.social),
                userConsent = UserConsentMapper.mapToDomainEntity(userJpaEntity.userConsent),
                createdAt = userJpaEntity.baseEntity.createdAt,
                updatedAt = userJpaEntity.baseEntity.updatedAt,
                withdrawAt = userJpaEntity.withdrawAt,
            )


        fun mapToJpaEntity(user: User): UserJpaEntity =
            UserJpaEntity(
                id = user.id,
                email = user.email,
                password = user.password,
                name = user.name,
                introduction = user.introduction,
                gender = user.gender,
                schoolId = user.schoolId,
                grade = user.grade,
                classNumber = user.classNumber,
                role = user.role,
                setting = SettingMapper.mapToJpaEntity(user.setting),
                profile = ProfileMapper.mapToJpaEntity(user.profile),
                fcm = FCMMapper.mapToJpaEntity(user.fcm),
                social = SocialMapper.mapToJpaEntity(user.social),
                userConsent = UserConsentMapper.mapToJpaEntity(user.userConsent),
                withdrawAt = user.withdrawAt,
                baseEntity = BaseEntity(
                    createdAt = user.createdAt,
                    updatedAt = user.updatedAt,
                ),
            )
}

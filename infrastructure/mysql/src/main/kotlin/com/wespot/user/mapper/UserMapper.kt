package com.wespot.user.mapper

import com.wespot.common.BaseEntity
import com.wespot.school.SchoolJpaEntity
import com.wespot.school.SchoolMapper
import com.wespot.user.User
import com.wespot.user.UserIntroduction
import com.wespot.user.entity.UserJpaEntity

object UserMapper {

    fun mapToDomainEntity(userJpaEntity: UserJpaEntity, schoolJpaEntity: SchoolJpaEntity): User =
        User(
            id = userJpaEntity.id,
            email = userJpaEntity.email,
            password = userJpaEntity.password,
            name = userJpaEntity.name,
            introduction = UserIntroduction.from(userJpaEntity.introduction),
            gender = userJpaEntity.gender,
            school = SchoolMapper.mapToDomainEntity(schoolJpaEntity),
            grade = userJpaEntity.grade,
            classNumber = userJpaEntity.classNumber,
            role = userJpaEntity.role,
            setting = SettingMapper.mapToDomainEntity(userJpaEntity.setting),
            profile = ProfileMapper.mapToDomainEntity(userJpaEntity.profile),
            fcm = FCMMapper.mapToDomainEntity(userJpaEntity.fcm),
            social = SocialMapper.mapToDomainEntity(userJpaEntity.social),
            userConsent = UserConsentMapper.mapToDomainEntity(userJpaEntity.userConsent),
            restriction = RestrictionMapper.mapToDomainEntity(userJpaEntity.restriction),
            createdAt = userJpaEntity.baseEntity.createdAt,
            updatedAt = userJpaEntity.baseEntity.updatedAt,
            withdrawalStatus = userJpaEntity.withdrawalStatus,
            withdrawalRequestAt = userJpaEntity.withdrawalRequestAt,
            withdrawalCancelAt = userJpaEntity.withdrawalCancelAt,
            withdrawalCompleteAt = userJpaEntity.withdrawalCompleteAt,
        )


    fun mapToJpaEntity(user: User): UserJpaEntity =
        UserJpaEntity(
            id = user.id,
            email = user.email,
            password = user.password,
            name = user.name,
            introduction = user.introduction.introduction,
            gender = user.gender,
            schoolId = user.school.id,
            grade = user.grade,
            classNumber = user.classNumber,
            role = user.role,
            setting = SettingMapper.mapToJpaEntity(user.setting),
            profile = ProfileMapper.mapToJpaEntity(user.profile),
            fcm = FCMMapper.mapToJpaEntity(user.fcm),
            social = SocialMapper.mapToJpaEntity(user.social),
            userConsent = UserConsentMapper.mapToJpaEntity(user.userConsent),
            restriction = RestrictionMapper.mapToJpaEntity(user.restriction),
            withdrawalStatus = user.withdrawalStatus,
            withdrawalRequestAt = user.withdrawalRequestAt,
            withdrawalCancelAt = user.withdrawalCancelAt,
            withdrawalCompleteAt = user.withdrawalCompleteAt,
            baseEntity = BaseEntity(
                createdAt = user.createdAt,
                updatedAt = user.updatedAt,
            ),
        )
}

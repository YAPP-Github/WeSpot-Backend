package com.wespot.user.mapper

import com.wespot.common.BaseEntity
import com.wespot.user.User
import com.wespot.user.entity.RestrictionJpaEntity
import com.wespot.user.entity.UserJpaEntity
import com.wespot.user.restriction.MessageRestriction
import com.wespot.user.restriction.Restriction
import com.wespot.user.restriction.VoteRestriction

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
            restriction = Restriction(
                voteRestriction = VoteRestriction(
                    userJpaEntity.restriction.voteRestrictionType,
                    userJpaEntity.restriction.voteReleaseDate
                ),
                messageRestriction = MessageRestriction(
                    userJpaEntity.restriction.messageRestrictionType,
                    userJpaEntity.restriction.messageReleaseDate
                ),
            ),
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
            restriction = RestrictionJpaEntity(
                voteRestrictionType = user.restriction.voteRestriction.restrictionType,
                voteReleaseDate = user.restriction.voteRestriction.releaseDate,
                messageRestrictionType = user.restriction.messageRestriction.restrictionType,
                messageReleaseDate = user.restriction.messageRestriction.releaseDate,
            ),
            withdrawAt = user.withdrawAt,
            baseEntity = BaseEntity(
                createdAt = user.createdAt,
                updatedAt = user.updatedAt,
            ),
        )
}

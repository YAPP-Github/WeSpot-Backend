package com.wespot.user.mapper

import com.wespot.common.BaseEntity
import com.wespot.school.School
import com.wespot.school.SchoolJpaEntity
import com.wespot.user.*
import com.wespot.user.entity.*

object UserMapper {

        fun mapToDomainEntity(user: UserJpaEntity): User =
            User(
                id = user.id,
                email = user.email,
                password = user.password,
                setting = Setting(
                    isEnableNotification = user.setting.isEnableNotification,
                ),
                grade = user.grade,
                groupNumber = user.groupNumber,
                role = user.role,
                social = Social(
                    socialType = user.social.socialType,
                    socialId = user.social.socialId,
                    socialEmail = user.social.socialEmail,
                    socialRefreshToken = user.social.socialRefreshToken,
                ),
                createdAt = user.baseEntity.createdAt,
                updatedAt = user.baseEntity.updatedAt,
                withdrawAt = user.withdrawAt,
            )


        fun mapToJpaEntity(user: User): UserJpaEntity =
            UserJpaEntity(
                id = user.id,
                email = user.email,
                password = user.password,
                setting = SettingJpaEntity(
                    isEnableNotification = user.setting.isEnableNotification,
                ),
                grade = user.grade,
                groupNumber = user.groupNumber,
                role = user.role,
                social = SocialJpaEntity(
                    socialType = user.social.socialType,
                    socialId = user.social.socialId,
                    socialEmail = user.social.socialEmail,
                    socialRefreshToken = user.social.socialRefreshToken,
                ),
                withdrawAt = user.withdrawAt,
                baseEntity = BaseEntity(
                    createdAt = user.createdAt,
                    updatedAt = user.updatedAt,
                ),
            )
}
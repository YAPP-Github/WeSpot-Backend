package com.wespot.auth

object PersonalInfoMapper {

    fun mapToDomainEntity(personalInfoJpaEntity: PersonalInfoJpaEntity): PersonalInfo {
        return PersonalInfo(
            id = personalInfoJpaEntity.id,
            email = personalInfoJpaEntity.email,
            name = personalInfoJpaEntity.name,
            socialId = personalInfoJpaEntity.socialId,
            socialEmail = personalInfoJpaEntity.socialEmail,
            socialRefreshToken = personalInfoJpaEntity.socialRefreshToken,
            restriction = personalInfoJpaEntity.restriction,
            storedAt = personalInfoJpaEntity.storedAt
        )
    }

    fun mapToJpaEntity(personalInfo: PersonalInfo): PersonalInfoJpaEntity {
        return PersonalInfoJpaEntity(
            id = personalInfo.id,
            email = personalInfo.email,
            name = personalInfo.name,
            socialId = personalInfo.socialId,
            socialEmail = personalInfo.socialEmail,
            socialRefreshToken = personalInfo.socialRefreshToken,
            restriction = personalInfo.restriction,
            storedAt = personalInfo.storedAt
        )
    }
}

package com.wespot.user.mapper

import com.wespot.user.UserConsent
import com.wespot.user.entity.UserConsentJpaEntity

object UserConsentMapper {

    fun mapToDomainEntity(userConsentJpaEntity: UserConsentJpaEntity): UserConsent =
        UserConsent(
            id = userConsentJpaEntity.id,
            consentType = userConsentJpaEntity.consentType,
            consentValue = userConsentJpaEntity.consentValue,
            consentedAt = userConsentJpaEntity.consentedAt,
        )


    fun mapToJpaEntity(userConsent: UserConsent): UserConsentJpaEntity =
        UserConsentJpaEntity(
            id = userConsent.id,
            consentType = userConsent.consentType,
            consentValue = userConsent.consentValue,
            consentedAt = userConsent.consentedAt,
        )
}
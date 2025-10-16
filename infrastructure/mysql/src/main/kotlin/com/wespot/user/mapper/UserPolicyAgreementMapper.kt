package com.wespot.user.mapper

import com.wespot.user.UserPolicyAgreement
import com.wespot.user.entity.UserPolicyAgreementJpaEntity

object UserPolicyAgreementMapper {

    fun toDomain(entity: UserPolicyAgreementJpaEntity): UserPolicyAgreement {
        return UserPolicyAgreement(
            id = entity.id,
            userId = entity.userId,
            policyType = entity.policyType
        )
    }

    fun toEntity(domain: UserPolicyAgreement): UserPolicyAgreementJpaEntity {
        return UserPolicyAgreementJpaEntity(
            id = domain.id,
            userId = domain.userId,
            policyType = domain.policyType
        )
    }

}

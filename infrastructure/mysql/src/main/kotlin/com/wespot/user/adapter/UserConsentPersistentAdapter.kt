package com.wespot.user.adapter

import com.wespot.user.UserConsent
import com.wespot.user.mapper.UserConsentMapper
import com.wespot.user.port.out.UserConsentPort
import com.wespot.user.repository.UserConsentJpaRepository
import org.springframework.stereotype.Repository

@Repository
class UserConsentPersistentAdapter(
    private val userConsentJpaRepository: UserConsentJpaRepository
) : UserConsentPort {

    override fun save(userConsent: UserConsent): UserConsent {
        return userConsentJpaRepository.save(UserConsentMapper.mapToJpaEntity(userConsent))
            .let { UserConsentMapper.mapToDomainEntity(it) }
    }

}

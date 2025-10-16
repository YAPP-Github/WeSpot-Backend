package com.wespot.user.adapter

import com.wespot.user.UserPolicyAgreement
import com.wespot.user.mapper.UserPolicyAgreementMapper
import com.wespot.user.port.out.UserPolicyAgreementPort
import com.wespot.user.repository.UserPolicyAgreementJpaRepository
import org.springframework.stereotype.Repository

@Repository
class UserPolicyAgreementAdapter(
    private val userPolicyAgreementJpaRepository: UserPolicyAgreementJpaRepository
) : UserPolicyAgreementPort {

    override fun save(userPolicyAgreement: UserPolicyAgreement): UserPolicyAgreement {
        val entity = UserPolicyAgreementMapper.toEntity(userPolicyAgreement)
        val savedEntity = userPolicyAgreementJpaRepository.save(entity)

        return UserPolicyAgreementMapper.toDomain(savedEntity)
    }

}

package com.wespot.user.repository

import com.wespot.user.entity.UserPolicyAgreementJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserPolicyAgreementJpaRepository : JpaRepository<UserPolicyAgreementJpaEntity, Long> {

    fun findAllByUserId(userId: Long): List<UserPolicyAgreementJpaEntity>

}

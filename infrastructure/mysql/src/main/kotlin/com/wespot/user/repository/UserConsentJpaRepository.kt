package com.wespot.user.repository

import com.wespot.user.entity.UserConsentJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserConsentJpaRepository: JpaRepository<UserConsentJpaEntity, Long> {
}
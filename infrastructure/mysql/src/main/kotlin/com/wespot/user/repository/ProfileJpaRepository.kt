package com.wespot.user.repository

import com.wespot.user.entity.ProfileJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProfileJpaRepository: JpaRepository<ProfileJpaEntity, Long> {
}
package com.wespot.user.repository

import com.wespot.user.entity.ProfileBackgroundJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProfileBackgroundJpaRepository: JpaRepository<ProfileBackgroundJpaEntity, Long>{
}

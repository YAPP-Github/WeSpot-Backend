package com.wespot.user.repository

import com.wespot.user.entity.ProfileIconJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProfileIconJpaRepository : JpaRepository<ProfileIconJpaEntity, Long>

package com.wespot.user.repository

import com.wespot.user.entity.RestrictionJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RestrictionJpaRepository : JpaRepository<RestrictionJpaEntity, Long> {
}

package com.wespot.user.repository

import com.wespot.user.entity.message.AnonymousProfileJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AnonymousProfileJpaRepository : JpaRepository<AnonymousProfileJpaEntity, Long> {
}

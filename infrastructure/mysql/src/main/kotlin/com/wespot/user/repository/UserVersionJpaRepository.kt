package com.wespot.user.repository

import com.wespot.user.entity.UserVersionJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserVersionJpaRepository : JpaRepository<UserVersionJpaEntity, Long> {

    fun findByUserId(id: Long): UserVersionJpaEntity?

    fun deleteByUserId(userId: Long)

}

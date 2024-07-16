package com.wespot.user.repository

import com.wespot.user.entity.UserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserJpaEntity, Long> {

    fun findByEmail(email: String): UserJpaEntity?

}
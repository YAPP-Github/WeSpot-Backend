package com.wespot.auth

import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenJpaRepository : JpaRepository<RefreshTokenJpaEntity, Long> {

    fun save(refreshToken: RefreshTokenJpaEntity): RefreshTokenJpaEntity

    fun deleteByUserId(userId: Long)

    fun findByUserId(userId: Long): RefreshTokenJpaEntity?

    fun findByRefreshToken(refreshToken: String): RefreshTokenJpaEntity?

}

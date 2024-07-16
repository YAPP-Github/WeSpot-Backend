package com.wespot.auth

import com.wespot.auth.port.out.RefreshTokenPort
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class RefreshTokenPersistenceAdapter(
    private val refreshTokenJpaRepository: RefreshTokenJpaRepository
) : RefreshTokenPort {


    @Transactional
    override fun save(refreshToken: RefreshToken): RefreshToken {
        return RefreshTokenMapper.mapToJpaEntity(refreshToken)
            .let { refreshTokenJpaRepository.save(it) }
            .let { RefreshTokenMapper.mapToDomainEntity(it) }
    }

    override fun findByUserId(userId: Long): RefreshToken? {
        return refreshTokenJpaRepository.findByUserId(userId)
            ?.let { RefreshTokenMapper.mapToDomainEntity(it) }
    }

    @Transactional
    override fun saveOrUpdate(refreshToken: RefreshToken): RefreshToken {
        val refreshTokenJpaEntity = refreshTokenJpaRepository.findByUserId(refreshToken.user.id)
        return if (refreshTokenJpaEntity != null) {
            refreshTokenJpaEntity.update(refreshToken.refreshToken)
            refreshTokenJpaRepository.save(refreshTokenJpaEntity)
                .let { RefreshTokenMapper.mapToDomainEntity(it) }
        } else {
            save(refreshToken)
        }
    }

    override fun deleteByUserId(userId: Long) {
        refreshTokenJpaRepository.deleteByUserId(userId)
    }

    override fun findByRefreshToken(refreshToken: String): RefreshToken? {
        val findByRefreshToken = refreshTokenJpaRepository.findByRefreshToken(refreshToken)
        if (findByRefreshToken != null) {
            return RefreshTokenMapper.mapToDomainEntity(findByRefreshToken)
        }
        return null
    }

}
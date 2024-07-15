package com.wespot.auth

import com.wespot.auth.port.out.RefreshTokenPort
import com.wespot.user.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Repository
class RefreshTokenPersistenceAdapter(
    private val refreshTokenRepository: RefreshTokenJpaRepository
) : RefreshTokenPort {
    override fun create(refreshToken: RefreshToken): RefreshToken {
        return RefreshTokenMapper.mapToJpaEntity(refreshToken)
            .let { refreshTokenRepository.save(it) }
            .let { RefreshTokenMapper.mapToDomainEntity(it) }
    }

    override fun findByUser(user: User): RefreshToken? {
        return refreshTokenRepository.findByIdOrNull(user.id)
            ?.let { RefreshTokenMapper.mapToDomainEntity(it) }
            ?: throw NoSuchElementException("해당하는 유저의 RefreshToken이 없습니다.")
    }

    override fun saveOrUpdate(refreshToken: RefreshToken): RefreshToken {
        val refreshTokenJpaEntity = refreshTokenRepository.findByUserId(refreshToken.user.id)
        return if (refreshTokenJpaEntity != null) {
            RefreshTokenMapper.mapToJpaEntity(refreshToken)
                .let { refreshTokenRepository.save(it) }
                .let { RefreshTokenMapper.mapToDomainEntity(it) }
        } else {
            create(refreshToken)
        }
    }

    override fun deleteByUserId(userId: Long) {
        refreshTokenRepository.deleteByUserId(userId)
    }

    override fun findByRefreshToken(refreshToken: String): RefreshToken? {
        val findByRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken)
        if (findByRefreshToken != null) {
            return RefreshTokenMapper.mapToDomainEntity(findByRefreshToken)
        }
        return null
    }

}
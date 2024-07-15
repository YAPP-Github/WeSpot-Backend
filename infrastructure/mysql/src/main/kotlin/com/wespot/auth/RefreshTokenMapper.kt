package com.wespot.auth

import com.wespot.user.mapper.UserMapper

object RefreshTokenMapper {

    fun mapToJpaEntity(refreshToken: RefreshToken): RefreshTokenJpaEntity {
        return RefreshTokenJpaEntity(
            id = refreshToken.id,
            refreshToken = refreshToken.refreshToken,
            user = UserMapper.mapToJpaEntity(refreshToken.user),
            expiredAt = refreshToken.expiredAt
        )
    }

    fun mapToDomainEntity(refreshTokenJpaEntity: RefreshTokenJpaEntity): RefreshToken {
        return RefreshToken(
            id = refreshTokenJpaEntity.id,
            refreshToken = refreshTokenJpaEntity.refreshToken,
            user = UserMapper.mapToDomainEntity(refreshTokenJpaEntity.user),
            expiredAt = refreshTokenJpaEntity.expiredAt
        )
    }
}
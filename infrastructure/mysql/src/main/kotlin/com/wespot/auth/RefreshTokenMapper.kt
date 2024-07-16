package com.wespot.auth

import com.wespot.common.BaseEntity
import com.wespot.user.mapper.UserMapper

object RefreshTokenMapper {

    fun mapToDomainEntity(refreshTokenJpaEntity: RefreshTokenJpaEntity): RefreshToken {
        return RefreshToken(
            id = refreshTokenJpaEntity.id,
            refreshToken = refreshTokenJpaEntity.refreshToken,
            user = UserMapper.mapToDomainEntity(refreshTokenJpaEntity.user),
            createdAt = refreshTokenJpaEntity.baseEntity.createdAt,
            updatedAt = refreshTokenJpaEntity.baseEntity.updatedAt,
            expiredAt = refreshTokenJpaEntity.expiredAt
        )
    }

    fun mapToJpaEntity(refreshToken: RefreshToken): RefreshTokenJpaEntity {
        return RefreshTokenJpaEntity(
            id = refreshToken.id,
            refreshToken = refreshToken.refreshToken,
            user = UserMapper.mapToJpaEntity(refreshToken.user),
            baseEntity = BaseEntity(
                createdAt = refreshToken.createdAt,
                updatedAt = refreshToken.updatedAt,
            ),
            expiredAt = refreshToken.expiredAt
        )
    }

}
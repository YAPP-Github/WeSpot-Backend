package com.wespot.user.mapper

import com.wespot.user.ProfileBackground
import com.wespot.user.entity.ProfileBackgroundJpaEntity

object ProfileBackgroundMapper {

    fun mapToDomainEntity(profileBackgroundJpaEntity: ProfileBackgroundJpaEntity): ProfileBackground =
        ProfileBackground(
            id = profileBackgroundJpaEntity.id,
            name = profileBackgroundJpaEntity.name,
            backgroundColor = profileBackgroundJpaEntity.backgroundColor
        )

    fun mapToJpaEntity(profileBackground: ProfileBackground): ProfileBackgroundJpaEntity =
        ProfileBackgroundJpaEntity(
            id = profileBackground.id,
            name = profileBackground.name,
            backgroundColor = profileBackground.backgroundColor
        )
}

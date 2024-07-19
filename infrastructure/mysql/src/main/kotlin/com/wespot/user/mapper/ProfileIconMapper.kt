package com.wespot.user.mapper

import com.wespot.user.ProfileIcon
import com.wespot.user.entity.ProfileIconJpaEntity

object ProfileIconMapper {

    fun mapToDomainEntity(profileIconJpaEntity: ProfileIconJpaEntity): ProfileIcon =
        ProfileIcon(
            id = profileIconJpaEntity.id,
            name = profileIconJpaEntity.name,
            iconUrl = profileIconJpaEntity.iconUrl
        )

    fun mapToJpaEntity(profileIcon: ProfileIcon): ProfileIconJpaEntity =
        ProfileIconJpaEntity(
            id = profileIcon.id,
            name = profileIcon.name,
            iconUrl = profileIcon.iconUrl
        )
}

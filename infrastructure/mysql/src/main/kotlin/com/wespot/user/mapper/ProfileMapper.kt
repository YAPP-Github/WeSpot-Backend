package com.wespot.user.mapper

import com.wespot.user.Profile
import com.wespot.user.entity.ProfileJpaEntity

object ProfileMapper {

    fun mapToDomainEntity(profile: ProfileJpaEntity): Profile =
        Profile(
            id = profile.id,
            user = UserMapper.mapToDomainEntity(profile.user),
            backgroundColor = profile.backgroundColor,
            iconUrl = profile.iconUrl
        )

    fun mapToJpaEntity(profile: Profile): ProfileJpaEntity =
        ProfileJpaEntity(
            id = profile.id,
            user = UserMapper.mapToJpaEntity(profile.user),
            backgroundColor = profile.backgroundColor,
            iconUrl = profile.iconUrl
        )
}
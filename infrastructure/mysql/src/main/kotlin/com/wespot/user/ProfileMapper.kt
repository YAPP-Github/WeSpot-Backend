package com.wespot.user

object ProfileMapper {

    fun mapToDomainEntity(profileJpaEntity: ProfileJpaEntity): Profile =
        Profile(
            id = profileJpaEntity.id,
            userId = profileJpaEntity.userId,
            backgroundColor = profileJpaEntity.backgroundColor,
            iconUrl = profileJpaEntity.iconUrl
        )

    fun mapToJpaEntity(profile: Profile): ProfileJpaEntity =
        ProfileJpaEntity(
            id = profile.id,
            userId = profile.userId,
            backgroundColor = profile.backgroundColor,
            iconUrl = profile.iconUrl,
        )

}

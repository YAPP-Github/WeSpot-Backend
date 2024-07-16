package com.wespot.user

object SocialMapper {

    fun mapToDomainEntity(socialJpaEntity: SocialJpaEntity): Social =
        Social(
            socialId = socialJpaEntity.socialId,
            socialType = socialJpaEntity.socialType,
            socialRefreshToken = socialJpaEntity.socialRefreshToken
        )

    fun mapToJpaEntity(social: Social): SocialJpaEntity =
        SocialJpaEntity(
            socialId = social.socialId,
            socialType = social.socialType,
            socialRefreshToken = social.socialRefreshToken
        )

}

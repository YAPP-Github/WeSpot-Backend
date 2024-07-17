package com.wespot.user.mapper

import com.wespot.user.Social
import com.wespot.user.entity.SocialJpaEntity

object SocialMapper {

    fun mapToDomainEntity(socialJpaEntity: SocialJpaEntity): Social =
        Social(
            socialId = socialJpaEntity.socialId,
            socialType = socialJpaEntity.socialType,
            socialEmail = socialJpaEntity.socialEmail,
            socialRefreshToken = socialJpaEntity.socialRefreshToken
        )

    fun mapToJpaEntity(social: Social): SocialJpaEntity =
        SocialJpaEntity(
            socialId = social.socialId,
            socialType = social.socialType,
            socialEmail = social.socialEmail,
            socialRefreshToken = social.socialRefreshToken
        )

}

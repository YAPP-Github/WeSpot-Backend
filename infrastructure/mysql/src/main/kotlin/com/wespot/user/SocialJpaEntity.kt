package com.wespot.user

import jakarta.persistence.Embeddable
import org.jetbrains.annotations.NotNull

@Embeddable
class SocialJpaEntity(

    @field: NotNull
    val socialId: Long,

    @field: NotNull
    val socialType: SocialType,

    val socialRefreshToken: String

)
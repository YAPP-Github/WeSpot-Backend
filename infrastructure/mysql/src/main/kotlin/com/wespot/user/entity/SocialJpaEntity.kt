package com.wespot.user.entity

import com.wespot.user.SocialType
import jakarta.persistence.Embeddable
import org.jetbrains.annotations.NotNull

@Embeddable
class SocialJpaEntity(

    @field: NotNull
    val socialId: String,

    @field: NotNull
    val socialType: SocialType,

    val socialEmail: String?,

    val socialRefreshToken: String?

)
package com.wespot.user.entity

import jakarta.persistence.Embeddable
import org.jetbrains.annotations.NotNull

@Embeddable
class SettingJpaEntity(

    @field: NotNull
    val isEnableVoteNotification: Boolean,

    @field: NotNull
    val isEnableMessageNotification: Boolean

)

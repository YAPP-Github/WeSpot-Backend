package com.wespot.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.jetbrains.annotations.NotNull

@Embeddable
class SettingJpaEntity(

    @field: NotNull
    val isEnableVoteNotification: Boolean,

    @field: NotNull
    val isEnableMessageNotification: Boolean,

    @field: NotNull
    val isEnableMarketingNotification: Boolean,

    @field: NotNull
    @Column(name = "is_enable_message_v2")
    val isEnableMessageV2: Boolean,

    @field: NotNull
    val isEnablePostNotification: Boolean

)

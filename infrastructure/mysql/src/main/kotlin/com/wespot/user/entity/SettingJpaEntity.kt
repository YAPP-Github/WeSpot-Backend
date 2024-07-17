package com.wespot.user.entity

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Embeddable
class SettingJpaEntity(

    @field: NotNull
    val isEnableNotification: Boolean

)

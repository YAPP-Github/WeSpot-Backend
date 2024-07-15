package com.wespot.user

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "setting")
class SettingJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @field: NotNull
    val isEnableNotification: Boolean

)

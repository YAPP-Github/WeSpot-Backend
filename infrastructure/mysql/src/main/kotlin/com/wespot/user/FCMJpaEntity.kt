package com.wespot.user

import com.wespot.common.BaseEntity
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
class FCMJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field: NotNull
    val fcmToken: String,

    @Embedded
    @field: NotNull
    val baseEntity: BaseEntity

)

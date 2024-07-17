package com.wespot.user.entity

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "profile")
class ProfileJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field: NotNull
    val backgroundColor: String,

    @field: NotNull
    val iconUrl: String

)

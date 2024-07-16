package com.wespot.user.entity

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "profile")
class ProfileJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", foreignKey = ForeignKey(name = "fk_profile_users_id"))
    val user: UserJpaEntity,

    @field: NotNull
    val backgroundColor: String,

    @field: NotNull
    val iconUrl: String

)

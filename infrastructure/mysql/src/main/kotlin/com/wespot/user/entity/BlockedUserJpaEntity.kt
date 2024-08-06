package com.wespot.user.entity

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime

@Entity
@Table(name = "blocked_user")
class BlockedUserJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field:NotNull
    val blockerId: Long,

    @field:NotNull
    val blockedId: Long,

    @field:NotNull
    val messageId: Long,

    @field:NotNull
    val createdAt: LocalDateTime
)

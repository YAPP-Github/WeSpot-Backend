package com.wespot.vote

import com.wespot.common.BaseEntity
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "ballot")
class BallotJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field: NotNull
    val voteId: Long,

    @field: NotNull
    val senderId: Long,

    @field: NotNull
    val receiverId: Long,

    @field: NotNull
    @Embedded
    val baseEntity: BaseEntity,

    @field: NotNull
    val isReceiverRead: Boolean

)

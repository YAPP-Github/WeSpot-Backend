package com.wespot.voteoption

import com.wespot.common.BaseEntity
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "vote_option")
class VoteOptionJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @field: NotNull
    val content: String,

    @field: NotNull
    @Embedded
    val baseEntity: BaseEntity

)

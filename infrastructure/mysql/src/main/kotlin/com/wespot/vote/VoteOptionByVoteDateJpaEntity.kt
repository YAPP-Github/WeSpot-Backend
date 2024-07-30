package com.wespot.vote

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "vote_option_by_date")
class VoteOptionByVoteDateJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field: NotNull
    val voteId: Long,

    @field: NotNull
    val voteOptionId: Long

)

package com.wespot.vote

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime

@Entity
@Table(name = "vote")
class VoteJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field: NotNull
    val schoolName: String,

    @field: NotNull
    val grade: Int,

    @field: NotNull
    val group: Int,

    @field: NotNull
    val date: LocalDateTime,

    @field: NotNull
    @OneToMany(mappedBy = "vote", cascade = [CascadeType.PERSIST])
    val ballots: List<BallotJpaEntity>

)


package com.wespot.vote

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.time.LocalDate

@Entity
@Table(name = "vote")
class VoteJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field: NotNull
    val schoolId: Long,

    @field: NotNull
    val grade: Int,

    @field: NotNull
    val groupNumber: Int,

    @field: NotNull
    val voteNumber: Int,

    @field: NotNull
    val date: LocalDate,

//    @field: NotNull
//    @OneToMany(mappedBy = "vote", cascade = [CascadeType.PERSIST])
//    val ballots: List<BallotJpaEntity>,

)

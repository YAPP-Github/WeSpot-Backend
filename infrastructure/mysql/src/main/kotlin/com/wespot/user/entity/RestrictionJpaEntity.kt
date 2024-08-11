package com.wespot.user.entity

import com.wespot.user.RestrictionType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.jetbrains.annotations.NotNull
import java.time.LocalDate

@Entity
@Table(name = "restriction")
data class RestrictionJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Enumerated(EnumType.STRING)
    @field: NotNull
    val voteRestrictionType: RestrictionType,

    @field: NotNull
    val voteReleaseDate: LocalDate,

    @Enumerated(EnumType.STRING)
    @field: NotNull
    val messageRestrictionType: RestrictionType,

    @field: NotNull
    val messageReleaseDate: LocalDate

)

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
@Table(name = "profile")
class RestrictionJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Enumerated(EnumType.STRING)
    @field: NotNull
    var voteRestrictionType: RestrictionType,

    @field: NotNull
    var voteReleaseDate: LocalDate,

    @Enumerated(EnumType.STRING)
    @field: NotNull
    var messageRestrictionType: RestrictionType,

    @field: NotNull
    var messageReleaseDate: LocalDate

) {
}

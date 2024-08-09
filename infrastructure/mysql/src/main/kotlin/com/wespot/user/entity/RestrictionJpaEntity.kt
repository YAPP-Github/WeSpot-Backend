package com.wespot.user.entity

import com.wespot.user.RestrictionType
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import org.jetbrains.annotations.NotNull
import java.time.LocalDate

@Embeddable
class RestrictionJpaEntity(

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

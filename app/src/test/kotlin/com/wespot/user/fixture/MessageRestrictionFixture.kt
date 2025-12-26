package com.wespot.user.fixture

import com.wespot.user.RestrictionType
import com.wespot.user.restriction.MessageRestriction
import java.time.LocalDate

object MessageRestrictionFixture {

    fun generate(
        restrictionType: RestrictionType = RestrictionType.NONE,
        releaseDate: LocalDate = LocalDate.of(9999, 12, 31),
    ): MessageRestriction {
        return MessageRestriction(
            restrictionType = restrictionType,
            releaseDate = releaseDate
        )
    }

}

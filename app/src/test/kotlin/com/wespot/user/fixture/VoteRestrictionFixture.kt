package com.wespot.user.fixture

import com.wespot.user.RestrictionType
import com.wespot.user.restriction.VoteRestriction
import java.time.LocalDate

object VoteRestrictionFixture {

    fun generate(
        restrictionType: RestrictionType = RestrictionType.NONE,
        releaseDate: LocalDate = LocalDate.of(9999, 12, 31),
    ): VoteRestriction {
        return VoteRestriction(
            restrictionType = restrictionType,
            releaseDate = releaseDate
        )
    }

}

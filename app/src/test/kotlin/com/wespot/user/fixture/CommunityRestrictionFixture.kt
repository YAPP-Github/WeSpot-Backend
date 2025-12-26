package com.wespot.user.fixture

import com.wespot.user.RestrictionType
import com.wespot.user.restriction.CommunityRestriction
import java.time.LocalDate

object CommunityRestrictionFixture {

    fun generate(
        restrictionType: RestrictionType = RestrictionType.NONE,
        releaseDate: LocalDate = LocalDate.of(9999, 12, 31),
    ): CommunityRestriction {
        return CommunityRestriction(
            restrictionType = restrictionType,
            releaseDate = releaseDate
        )
    }

}

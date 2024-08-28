package com.wespot.user.dto.response

import com.wespot.user.RestrictionType
import java.time.LocalDate

data class CheckedRestrictionResponse(
    val restrictionType: RestrictionType,
    val releaseDate: LocalDate,
) {
    companion object {
        fun from(userRestriction: Pair<RestrictionType, LocalDate>): CheckedRestrictionResponse {
            return CheckedRestrictionResponse(
                restrictionType = userRestriction.first,
                releaseDate = userRestriction.second
            )
        }
    }
}

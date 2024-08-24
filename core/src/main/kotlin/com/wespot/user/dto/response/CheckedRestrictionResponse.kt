package com.wespot.user.dto.response

import com.wespot.user.RestrictionType
import com.wespot.user.User
import java.time.LocalDate

data class CheckedRestrictionResponse(
    val messageRestrictionType: RestrictionType,
    val messageReleaseDate: LocalDate,
    val voteRestrictionType: RestrictionType,
    val voteReleaseDate: LocalDate,
) {

    companion object {
        fun from(user: User): CheckedRestrictionResponse {
            return CheckedRestrictionResponse(
                user.restriction.messageRestriction.restrictionType,
                user.restriction.messageRestriction.releaseDate,
                user.restriction.voteRestriction.restrictionType,
                user.restriction.voteRestriction.releaseDate
            )
        }
    }

}

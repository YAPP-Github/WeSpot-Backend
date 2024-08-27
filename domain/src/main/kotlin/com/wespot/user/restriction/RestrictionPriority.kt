package com.wespot.user.restriction

import com.wespot.user.RestrictionType
import com.wespot.user.User
import java.time.LocalDate

enum class RestrictionPriority(
    private val discriminationRestrictionType: (loginUser: User) -> Boolean,
    private val restrictionCalculator: (loginUser: User) -> Pair<RestrictionType, LocalDate>
) {
    FIRST_PRIORITY_RESTRICTION(
        { loginUser -> loginUser.restriction.messageRestriction.restrictionType == RestrictionType.PERMANENT_BAN_MESSAGE_REPORT },
        { loginUser ->
            Pair(
                RestrictionType.PERMANENT_BAN_MESSAGE_REPORT,
                loginUser.restriction.messageRestriction.releaseDate
            )
        }), // 가장 우선순위가 높은 쪽지로 인한 영구제재
    SECOND_PRIORITY_RESTRICTION(
        { loginUser -> loginUser.restriction.messageRestriction.restrictionType == RestrictionType.PERMANENT_BAN_VOTE_REPORT },
        { loginUser ->
            Pair(RestrictionType.PERMANENT_BAN_VOTE_REPORT, loginUser.restriction.voteRestriction.releaseDate)
        }), // 두 번째로 우선순위가 높은 투표로 인한 영구제재
    THIRD_PRIORITY_RESTRICTION(
        { loginUser -> loginUser.restriction.messageRestriction.restrictionType == RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT },
        { loginUser ->
            Pair(RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT, loginUser.restriction.messageRestriction.releaseDate)
        }), // 세 번째로 우선순위가 높은 쪽지로 인한 이용제한
    LAST_PRIORITY_RESTRICTION(
        { _ -> true },
        { _ ->
            Pair(RestrictionType.NONE, LocalDate.now())
        }); // 제재를 당하고 있지 않은 상태

    companion object {
        fun fromRestrictionPriority(loginUser: User): Pair<RestrictionType, LocalDate> {
            return entries.first { it.discriminationRestrictionType(loginUser) }
                .restrictionCalculator(loginUser)
        }
    }

}

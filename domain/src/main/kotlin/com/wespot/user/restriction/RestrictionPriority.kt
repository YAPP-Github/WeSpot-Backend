package com.wespot.user.restriction

import com.wespot.user.RestrictionType
import com.wespot.user.User
import java.time.LocalDate

enum class RestrictionPriority(
    private val discriminationRestrictionType: (loginUser: User) -> Boolean,
    private val restrictionCalculator: (loginUser: User) -> Pair<RestrictionType, LocalDate>
) {
    PRIORITY_RESTRICTION_1(
        { loginUser -> loginUser.restriction.messageRestriction.restrictionType == RestrictionType.PERMANENT_BAN_MESSAGE_REPORT },
        { loginUser ->
            Pair(
                RestrictionType.PERMANENT_BAN_MESSAGE_REPORT,
                loginUser.restriction.messageRestriction.releaseDate
            )
        }), // 가장 우선순위가 높은 쪽지로 인한 영구제재
    PRIORITY_RESTRICTION_2(
        { loginUser -> loginUser.restriction.voteRestriction.restrictionType == RestrictionType.PERMANENT_BAN_VOTE_REPORT },
        { loginUser ->
            Pair(RestrictionType.PERMANENT_BAN_VOTE_REPORT, loginUser.restriction.voteRestriction.releaseDate)
        }), // 두 번째로 우선순위가 높은 투표로 인한 영구제재
    PRIORITY_RESTRICTION_3(
        { loginUser -> loginUser.restriction.communityRestriction.restrictionType == RestrictionType.PERMANENT_BAN_COMMUNITY_REPORT },
        { loginUser ->
            Pair(RestrictionType.PERMANENT_BAN_COMMUNITY_REPORT, loginUser.restriction.communityRestriction.releaseDate)
        }), // 세 번째로 우선순위가 높은 커뮤니티로 인한 영구제재
    PRIORITY_RESTRICTION_4(
        { loginUser -> loginUser.restriction.messageRestriction.restrictionType == RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT },
        { loginUser ->
            Pair(RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT, loginUser.restriction.messageRestriction.releaseDate)
        }), // 네 번째로 우선순위가 높은 쪽지로 인한 이용제한
    PRIORITY_RESTRICTION_5(
        { loginUser -> loginUser.restriction.communityRestriction.restrictionType == RestrictionType.TEMPORARY_BAN_COMMUNITY_REPORT },
        { loginUser ->
            Pair(RestrictionType.TEMPORARY_BAN_COMMUNITY_REPORT, loginUser.restriction.communityRestriction.releaseDate)
        }), // 다섯 번째로 우선순위가 높은 쪽지로 인한 이용제한
    PRIORITY_RESTRICTION_6(
        { _ -> true },
        { _ ->
            Pair(RestrictionType.NONE, LocalDate.of(9999, 12, 31))
        }); // 제재를 당하고 있지 않은 상태

    companion object {
        fun fromRestrictionPriority(loginUser: User): Pair<RestrictionType, LocalDate> {
            return entries.first { it.discriminationRestrictionType(loginUser) }
                .restrictionCalculator(loginUser)
        }
    }

}

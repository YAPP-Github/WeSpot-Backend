package com.wespot.report

import com.wespot.user.restriction.RestrictionCategory

enum class ReportType(val value: String) {

    MESSAGE("쪽지"),
    VOTE("투표"),
    COMMUNITY("커뮤니티"),
    ;

    fun toRestrictionCategory(): RestrictionCategory {
        return when (this) {
            MESSAGE -> RestrictionCategory.MESSAGE
            VOTE -> RestrictionCategory.VOTE
            COMMUNITY -> RestrictionCategory.COMMUNITY
        }
    }

}

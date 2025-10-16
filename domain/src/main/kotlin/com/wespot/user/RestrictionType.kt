package com.wespot.user

enum class RestrictionType {
    NONE, // 제한 x

    PERMANENT_BAN_VOTE_REPORT, // 투표 제보로 인한 영구제한

    TEMPORARY_BAN_MESSAGE_REPORT, // 메시지 신고로 인한 제한
    PERMANENT_BAN_MESSAGE_REPORT, // 메시지 신고로 인한 영구제한

    TEMPORARY_BAN_COMMUNITY_REPORT,// 커뮤니티 관련 신고 및 차단으로 인한 제한
    PERMANENT_BAN_COMMUNITY_REPORT // 커뮤니티 관련 신고 및 차단으로 인한 영구제한
    ;

    fun isVoteRestriction(): Boolean {
        return this == PERMANENT_BAN_VOTE_REPORT
    }

    fun isMessageRestriction(): Boolean {
        return this == TEMPORARY_BAN_MESSAGE_REPORT || this == PERMANENT_BAN_MESSAGE_REPORT
    }

    fun isCommunityRestriction(): Boolean {
        return this == TEMPORARY_BAN_COMMUNITY_REPORT || this == PERMANENT_BAN_COMMUNITY_REPORT
    }

}

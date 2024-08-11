package com.wespot.user

enum class RestrictionType {
    NONE, // 제한 x
    PERMANENT_BAN_VOTE_REPORT, // 투표 제보로 인한 영구제한
    TEMPORARY_BAN_MESSAGE_REPORT, // 메시지 신고로 인한 제한
    PERMANENT_BAN_MESSAGE_REPORT; // 메시지 신고로 인한 영구제한

    // restriction type이 vote 인지, message 인지 확인하는 메서드를 작성해줘
    fun isVoteRestriction(): Boolean {
        return this == PERMANENT_BAN_VOTE_REPORT
    }

    fun isMessageRestriction(): Boolean {
        return this == TEMPORARY_BAN_MESSAGE_REPORT || this == PERMANENT_BAN_MESSAGE_REPORT
    }

}

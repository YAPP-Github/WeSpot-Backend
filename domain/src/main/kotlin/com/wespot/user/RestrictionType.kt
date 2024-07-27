package com.wespot.user

enum class RestrictionType {
    NONE, // 제한 x
    PERMANENT_BAN_VOTE_REPORT, // 투표 제보로 인한 영구제한
    TEMPORARY_BAN_MESSAGE_REPORT, // 메시지 신고로 인한 제한
    PERMANENT_BAN_MESSAGE_REPORT // 메시지 신고로 인한 영구제한,
}

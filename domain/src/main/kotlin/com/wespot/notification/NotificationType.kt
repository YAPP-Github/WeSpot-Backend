package com.wespot.notification

enum class NotificationType {

    VOTE, // 투표 하러 가기
    VOTE_RESULT, // 투표 전체 결과 조회
    VOTE_RECEIVED, // 받은 투표 조회
    MESSAGE, // 쪽지 보내러 가기
    MESSAGE_RECEIVED, // 받은 쪽지 확인
    MESSAGE_SENT; // 보낸 쪽지 확인

    fun isVote(): Boolean {
        return this == VOTE || this == VOTE_RESULT || this == VOTE_RECEIVED
    }

    fun isMessage(): Boolean {
        return this == MESSAGE || this == MESSAGE_RECEIVED || this == MESSAGE_SENT
    }

}

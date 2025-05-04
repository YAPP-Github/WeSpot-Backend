package com.wespot.notification

import com.wespot.user.User
import java.util.function.Predicate

enum class NotificationType(
    val isNotificationAllow: Predicate<User>
) {

    VOTE({ it.isEnableVoteNotification() }), // 투표 하러 가기
    VOTE_RESULT({ it.isEnableVoteNotification() }), // 투표 전체 결과 조회
    VOTE_RECEIVED({ it.isEnableVoteNotification() }), // 받은 투표 조회

    MESSAGE({ it.isEnableMessageNotification() }), // 쪽지 보내러 가기
    MESSAGE_RECEIVED({ it.isEnableMessageNotification() }), // 받은 쪽지 확인
    MESSAGE_SENT({ it.isEnableMessageNotification() }), // 보낸 쪽지 확인
    ANSWER_MESSAGE({ it.isEnableMessageNotification() }), // 쪽지 답장하기

    PROFILE_UPDATE({ it.isEnableMarketingNotification() }), // 프로필 업데이트 이벤트
    UPDATE_REQUIRED({ it.isEnableMarketingNotification() }), // 업데이트를 아직 안한 유저

    ;

    fun isVote(): Boolean {
        return this == VOTE || this == VOTE_RESULT || this == VOTE_RECEIVED
    }

    fun isMessage(): Boolean {
        return this == MESSAGE || this == MESSAGE_RECEIVED || this == MESSAGE_SENT
    }

}

package com.wespot.notification

import com.wespot.user.User
import java.util.function.Predicate

enum class NotificationType(
    val isNotificationAllow: Predicate<User>,
    val specificNotificationType: SpecificNotificationType
) {

    VOTE({ it.isEnableVoteNotification() }, SpecificNotificationType.VOTE), // 투표 하러 가기
    VOTE_RESULT({ it.isEnableVoteNotification() }, SpecificNotificationType.VOTE), // 투표 전체 결과 조회
    VOTE_RECEIVED({ it.isEnableVoteNotification() }, SpecificNotificationType.VOTE), // 받은 투표 조회

    MESSAGE({ it.isEnableMessageNotification() }, SpecificNotificationType.MESSAGE), // 쪽지 보내러 가기
    MESSAGE_RECEIVED({ it.isEnableMessageNotification() }, SpecificNotificationType.MESSAGE), // 받은 쪽지 확인
    MESSAGE_SENT({ it.isEnableMessageNotification() }, SpecificNotificationType.MESSAGE), // 보낸 쪽지 확인
    ANSWER_MESSAGE({ it.isEnableMessageNotification() }, SpecificNotificationType.MESSAGE), // 쪽지 답장하기

    PROFILE_UPDATE({ it.isEnableMarketingNotification() }, SpecificNotificationType.UPDATE), // 프로필 업데이트 이벤트
    UPDATE_REQUIRED({ it.isEnableMarketingNotification() }, SpecificNotificationType.UPDATE), // 업데이트를 아직 안한 유저
    ;

    fun isVote(): Boolean {
        return specificNotificationType == SpecificNotificationType.VOTE
    }

    fun isMessage(): Boolean {
        return specificNotificationType == SpecificNotificationType.MESSAGE
    }

}

package com.wespot.common

import com.wespot.common.link.DeepLink
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.notification.NotificationType
import org.springframework.http.HttpStatus

enum class NotificationTypeToUpdateFeatureContent(

    val notificationType: NotificationType,

    val textComponentText: String,

    val imageUrl: String,

    val skipButtonText: String,

    val movedToUpdatedFeatureViewButtonText: String,
    val movedToUpdatedFeatureViewButtonUrl: DeepLink

) {

    PROFILE_UPDATE(
        NotificationType.PROFILE_UPDATE,
        "새로운 기능",
        "https://dw2d2daekmyur.cloudfront.net/%E1%84%89%E1%85%A5%E1%84%87%E1%85%A5%E1%84%83%E1%85%B3%E1%84%85%E1%85%B5%E1%84%87%E1%85%B3%E1%86%AB_%E1%84%8B%E1%85%A5%E1%86%B8%E1%84%83%E1%85%A6%E1%84%8B%E1%85%B5%E1%84%90%E1%85%B3_%E1%84%8B%E1%85%B5%E1%84%86%E1%85%B5%E1%84%8C%E1%85%B5.png",
        "다음에 하기",
        "프로필 설정하기",
        DeepLink.PROFILE_IMAGE_UPDATE_URL,
    ),
    ;

    companion object {

        fun fromWithNotificationType(notificationType: NotificationType): NotificationTypeToUpdateFeatureContent {
            return entries.stream()
                .filter { it.notificationType == notificationType }
                .findFirst()
                .orElseThrow {
                    CustomException(
                        HttpStatus.BAD_REQUEST,
                        ExceptionView.TOAST,
                        "서버 드리븐 UI를 제공하지 않는 알림 타입입니다."
                    )
                }
        }

    }

}

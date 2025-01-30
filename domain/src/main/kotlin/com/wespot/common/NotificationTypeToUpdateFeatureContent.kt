package com.wespot.common

import com.wespot.common.link.DeepLink
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.notification.NotificationType
import org.springframework.http.HttpStatus

enum class NotificationTypeToUpdateFeatureContent(

    val notificationType: NotificationType,

    val titleText: String,

    val imageUrl: String,

    val skipButtonText: String,

    val movedToUpdatedFeatureViewButtonText: String,
    val movedToUpdatedFeatureViewButtonUrl: DeepLink

) {

    PROFILE_UPDATE(
        NotificationType.PROFILE_UPDATE,
        "새로운 기능",
        "https://google.com",
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

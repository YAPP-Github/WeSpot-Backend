package com.wespot.common

import com.wespot.view.button.link.DeepLink
import com.wespot.common.view.OnClickActionType
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.notification.NotificationType
import org.springframework.http.HttpStatus

enum class NotificationTypeToUpdateFeatureContent(

    val notificationType: NotificationType,

    val topBarComponentText: String,

    val titleComponentText: String,

    val subTitleComponentText: String,

    val imageComponentURL: String,
    val imageComponentWidth: Int,
    val imageComponentHeight: Int,

    val chipComponentText: String,

    val descriptionComponentText: String,

    val descriptionImageComponentURL: String,
    val descriptionImageComponentWidth: Int,
    val descriptionImageComponentHeight: Int,

    val buttonComponentProperties: List<List<Any>>
) {

    PROFILE_UPDATE(
        NotificationType.PROFILE_UPDATE,
        "새로운 기능",
        "이제 원하는 사진으로 프로필을 설정할 수 있어요",
        "나만의 특별한 프로필로 친구들에게 나를 소개해 보세요",
        "https://dw2d2daekmyur.cloudfront.net/IMAGE_UPDATE_UPPER.png",
        220, 268,
        "이렇게 활용돼요",
        "나만의 개성을 담은 특별한 프로필은 반 친구들 사이에서 나를 더 잘 표현해 줄 거에요",
        "https://dw2d2daekmyur.cloudfront.net/IMAGE_UPDATE_LOWER.png",
        220, 324,
        listOf(
            listOf("다음에 하기", "0xFFF7F7F8", "0xFF5A5C63", "0xFF48494C", OnClickActionType.NONE, DeepLink.NONE),
            listOf(
                "프로필 설정하기",
                "0xFF1B1C1E",
                "0xFFF6FE8B",
                "0xFFC0C66B",
                OnClickActionType.DEEP_LINK,
                DeepLink.PROFILE_IMAGE_UPDATE_URL
            ),
        )
    )
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

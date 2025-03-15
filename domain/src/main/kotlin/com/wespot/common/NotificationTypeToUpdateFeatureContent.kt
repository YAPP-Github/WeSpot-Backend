package com.wespot.common

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.notification.NotificationType
import com.wespot.view.TopBarComponent
import com.wespot.view.button.ButtonComponent
import com.wespot.view.button.ButtonsComponent
import com.wespot.view.button.link.DeepLink
import com.wespot.view.icon.Icon
import com.wespot.view.image.ImageComponent
import com.wespot.view.padding.Paddings
import com.wespot.view.text.ChipComponent
import com.wespot.view.text.RichText
import com.wespot.view.text.TextComponent
import org.springframework.http.HttpStatus

enum class NotificationTypeToUpdateFeatureContent(
    val notificationType: NotificationType,

    val topBarComponent: TopBarComponent,
    val textComponents: List<TextComponent>,
    val imageComponents: List<ImageComponent>,
    val chipComponent: ChipComponent,
    val buttonsComponent: ButtonsComponent
) {

    PROFILE_UPDATE(
        NotificationType.PROFILE_UPDATE,
        TopBarComponent.of(
            RichText.of("새로운 기능", "#FFF7F7F8", 18, "Center", "SemiBold"),
            listOf(Icon.ofWithClickType("https://dw2d2daekmyur.cloudfront.net/backIcon.png", 24, 24, "BackNavigation"))
        ),
        listOf(
            TextComponent.of(
                RichText.of("이제 원하는 사진으로\\n프로필을 설정할 수 있어요", "#FFF7F7F8", 20, "Start", "Bold"),
                Paddings.of(30, 30, 8)
            ),
            TextComponent.of(
                RichText.of("나만의 특별한 프로필로 친구들에게 나를 소개해 보세요", "#FF76777D", 14, "Start", "Medium"),
                Paddings.of(30, 30, 48)
            ),
            TextComponent.of(
                RichText.of("나만의 개성을 담은 특별한 프로필은\\n반 친구들 사이에서 나를 더 잘 표현해 줄 거예요", "#FFEAEBEC", 14, "Start", "SemiBold"),
                Paddings.of(30, 30, 50)
            ),
        ),
        listOf(
            ImageComponent.of(
                "https://dw2d2daekmyur.cloudfront.net/%E1%84%89%E1%85%A5%E1%84%87%E1%85%A5%E1%84%83%E1%85%B3%E1%84%85%E1%85%B5%E1%84%87%E1%85%B3%E1%86%AB_%E1%84%8B%E1%85%A5%E1%86%B8%E1%84%83%E1%85%A6%E1%84%8B%E1%85%B5%E1%84%90%E1%85%B3_%E1%84%8B%E1%85%B5%E1%84%86%E1%85%B5%E1%84%8C%E1%85%B5.png",
                220,
                268,
                Paddings.of(null, null, 32)
            ),
            ImageComponent.of(
                "https://dw2d2daekmyur.cloudfront.net/%E1%84%89%E1%85%A5%E1%84%87%E1%85%A5%E1%84%83%E1%85%B3%E1%84%85%E1%85%B5%E1%84%87%E1%85%B3%E1%86%AB_%E1%84%8B%E1%85%A5%E1%86%B8%E1%84%83%E1%85%A6%E1%84%8B%E1%85%B5%E1%84%90%E1%85%B3_%E1%84%8B%E1%85%B5%E1%84%86%E1%85%B5%E1%84%8C%E1%85%B5.png",
                220,
                324,
                Paddings.of(null, null, 118)
            )
        ),
        ChipComponent.of(
            RichText.of("이렇게 활용돼요", "#FFFFFFFF", 14, "Center", "SemiBold"),
            Paddings.of(30, null, 20),
            "#FF323439",
            "#FFF6FE8B"
        ),
        ButtonsComponent.of(
            listOf(
                ButtonComponent.of(
                    RichText.of("다음에 하기", "#FFF7F7F8", 16, "Center", "SemiBold"),
                    "#FF5A5C63",
                    "#FF48494C",
                ),
                ButtonComponent.ofWithDeepLink(
                    RichText.of("다음에 하기", "#FFF7F7F8", 16, "Center", "SemiBold"),
                    "#FF5A5C63",
                    "#FF48494C",
                    DeepLink.PROFILE_IMAGE_UPDATE_URL
                )
            ),
            Paddings.of(20, 20, 12)
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

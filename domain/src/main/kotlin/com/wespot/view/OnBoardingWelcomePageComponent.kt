package com.wespot.view

import com.wespot.view.button.ButtonComponent
import com.wespot.view.button.ButtonsComponent
import com.wespot.view.button.OnClickActionType
import com.wespot.view.image.ImageComponent
import com.wespot.view.padding.Paddings
import com.wespot.view.text.RichText
import com.wespot.view.text.TextComponent

data class OnBoardingWelcomePageComponent(
    val textComponent: TextComponent,
    val imageComponent: ImageComponent,
    val buttonsComponent: ButtonsComponent
) {

    companion object {

        fun createVoteComponent(): OnBoardingWelcomePageComponent {
            return OnBoardingWelcomePageComponent(
                textComponent = TextComponent.of(
                    RichText.of("지금 위스팟에\n 우리반 투표함이 열렸어요!", "#FFF7F7F8", 20, "Center", "Bold"),
                    Paddings.of(bottom = 8, top = 8)
                ),
                imageComponent = ImageComponent.of(
                    "https://dw2d2daekmyur.cloudfront.net/vote.png", 200, 200,
                    Paddings.of(top = 8, bottom = 8)
                ),
                buttonsComponent = ButtonsComponent.of(
                    listOf(
                        ButtonComponent.ofWithClickActionType(
                            RichText.of("우리반 비밀 투표가 무엇인가요?", "#FF1B1C1E", 16, "Center", "SemiBold"),
                            "#FFF6FE8B",
                            "#FFC0C66B",
                            OnClickActionType.ACTION,
                            Paddings.of(top = 16, bottom = 16)
                        )
                    ),
                    Paddings.of(start = 20, end = 20, bottom = 12, top = 12)
                )
            )
        }

        fun createMessageComponent(): OnBoardingWelcomePageComponent {
            return OnBoardingWelcomePageComponent(
                textComponent = TextComponent.of(
                    RichText.of("에버가 쪽지를 보냈네요!\n 열어서 확인해 볼까요?", "#FFF7F7F8", 20, "Center", "Bold"),
                    Paddings.of(bottom = 8)
                ),
                imageComponent = ImageComponent.of(
                    "https://dw2d2daekmyur.cloudfront.net/message.png", 200, 200,
                    Paddings.of(top = 8, bottom = 8)
                ),
                buttonsComponent = ButtonsComponent.of(
                    listOf(
                        ButtonComponent.ofWithClickActionType(
                            RichText.of("쪽지 알아보기", "#FF1B1C1E", 16, "Center", "SemiBold"),
                            "#FFF6FE8B",
                            "#FFC0C66B",
                            OnClickActionType.ACTION,
                            Paddings.of(top = 16, bottom = 16)
                        )
                    ),
                    Paddings.of(start = 20, end = 20, bottom = 12, top = 12)
                )
            )
        }

    }

}

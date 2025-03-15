package com.wespot.view

import com.wespot.view.button.ButtonComponent
import com.wespot.view.image.ImageComponent
import com.wespot.view.padding.Paddings
import com.wespot.view.text.RichText

data class OnBoardingImageComponent(
    val titleComponent: TitleComponent,
    val imageComponent: ImageComponent,
    val buttonComponent: ButtonComponent
) {

    companion object {

        fun createMessageComponent(): OnBoardingImageComponent {
            return OnBoardingImageComponent(
                titleComponent = TitleComponent.from("에버가 쪽지를 보냈네요! 열어서 확인해 볼까요?"),
                imageComponent = ImageComponent.of(
                    "https://dw2d2daekmyur.cloudfront.net/message.png", 300, 300,
                    Paddings.of(null, null, null)
                ),
                buttonComponent = ButtonComponent.of(
                    RichText.of("이해했어요", "#FFFFFFFF", 16, "Center", "bold"),
                    "#FFFFFFFF",
                    "#FFFFFFFF"
                )
            )
        }

        fun createVoteComponent(): OnBoardingImageComponent {
            return OnBoardingImageComponent(
                titleComponent = TitleComponent.from("지금 위스팟에 우리반 투표함이 열렸어요!"),
                imageComponent = ImageComponent.of(
                    "https://dw2d2daekmyur.cloudfront.net/vote.png",
                    300,
                    300,
                    Paddings.of(null, null, null)
                ),
                buttonComponent = ButtonComponent.of(
                    RichText.of("이해했어요", "#FFFFFFFF", 16, "Center", "bold"),
                    "#FFFFFFFF",
                    "#FFFFFFFF"
                )
            )
        }

    }

}

package com.wespot.common.view

data class OnBoardingImageComponent(
    val titleComponent: TitleComponent,
    val imageComponent: ImageComponent,
    val buttonComponent: ButtonComponent
) {

    companion object {

        fun createMessageComponent(): OnBoardingImageComponent {
            return OnBoardingImageComponent(
                titleComponent = TitleComponent.from("에버가 쪽지를 보냈네요! 열어서 확인해 볼까요?"),
                imageComponent = ImageComponent.of("https://dw2d2daekmyur.cloudfront.net/message.png", 100, 100),
                buttonComponent = ButtonComponent.from("쪽지 열어보기")
            )
        }

        fun createVoteComponent(): OnBoardingImageComponent {
            return OnBoardingImageComponent(
                titleComponent = TitleComponent.from("지금 위스팟에 우리반 투표함이 열렸어요!"),
                imageComponent = ImageComponent.of("https://dw2d2daekmyur.cloudfront.net/vote.png", 100, 100),
                buttonComponent = ButtonComponent.from("우리반 비밀 투표가 무엇인가요?")
            )
        }

    }

}

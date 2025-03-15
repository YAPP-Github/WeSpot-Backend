package com.wespot.view

data class OnBoardingDescriptionComponent(
    val titleComponent: com.wespot.view.TitleComponent,
    val textLinesComponent: com.wespot.view.TextLinesComponent,
    val buttonComponent: com.wespot.view.ButtonComponent
) {

    companion object {

        fun createMessageComponent(): com.wespot.view.OnBoardingDescriptionComponent {
            return com.wespot.view.OnBoardingDescriptionComponent(
                titleComponent = com.wespot.view.TitleComponent.Companion.from("쪽지에 대해 알려드릴게요"),
                textLinesComponent = com.wespot.view.TextLinesComponent.Companion.from(
                    textLines = listOf(
                        Pair(
                            "https://dw2d2daekmyur.cloudfront.net/message_01.png",
                            "학교와 학년에 상관없이 고마운 친구, 관심 있는 친구에게 내 마음을 전할 수 있어요"
                        ),
                        Pair(
                            "https://dw2d2daekmyur.cloudfront.net/message_02.png",
                            "마음함은 매일 17:00에 열리고 22:00에 닫혀요 서로의 마음은 22:00에 한꺼번에 전달돼요"
                        ),
                        Pair(
                            "https://dw2d2daekmyur.cloudfront.net/message_03.png",
                            "익명 쪽지로 내가 보냈다는 사실을 숨길 수 있어요 원한다면 실명을 드러낼 수도 있어요"
                        ),
                    )
                ),
                buttonComponent = com.wespot.view.ButtonComponent.Companion.from("이해했어요")
            )
        }

        fun createVoteComponent(): com.wespot.view.OnBoardingDescriptionComponent {
            return com.wespot.view.OnBoardingDescriptionComponent(
                titleComponent = com.wespot.view.TitleComponent.Companion.from("우리반 비밀 투표에 대해 알려드릴게요"),
                textLinesComponent = com.wespot.view.TextLinesComponent.Companion.from(
                    textLines = listOf(
                        Pair(
                            "https://dw2d2daekmyur.cloudfront.net/vote_01.png",
                            "비밀 투표는 반 친구들에 대해 서로 칭찬하는 문화를 만들기 위해 시작되었어요"
                        ),
                        Pair(
                            "https://dw2d2daekmyur.cloudfront.net/vote_02.png",
                            "비밀 투표함은 매일 밤 23:59에 닫히고 다음날 00:00에 새로운 비밀 투표함이 열려요"
                        ),
                        Pair(
                            "https://dw2d2daekmyur.cloudfront.net/vote_03.png",
                            "내가 어떤 친구에게 투표했는지는 보이지 않으니 안심하세요"
                        ),
                    )
                ),
                buttonComponent = com.wespot.view.ButtonComponent.Companion.from("이해했어요")
            )
        }

    }

}

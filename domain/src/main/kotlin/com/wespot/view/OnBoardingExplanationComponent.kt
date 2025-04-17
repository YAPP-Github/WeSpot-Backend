package com.wespot.view

import com.wespot.view.button.ButtonComponent
import com.wespot.view.button.ButtonsComponent
import com.wespot.view.button.OnClickActionType
import com.wespot.view.padding.Paddings
import com.wespot.view.text.RichText
import com.wespot.view.text.TextComponent
import com.wespot.view.text.TextListComponent

data class OnBoardingExplanationComponent(
    val textComponent: TextComponent,
    val textListComponent: TextListComponent,
    val buttonsComponent: ButtonsComponent
) {

    companion object {

        fun createVoteComponent(): OnBoardingExplanationComponent {
            return OnBoardingExplanationComponent(
                textComponent = TextComponent.of(
                    RichText.of("우리반 비밀 투표에 대해 알려드릴게요", "#FFF7F7F8", 20, "Center", "Bold"),
                    Paddings.of(null, null, bottom = 7, null)
                ),
                textListComponent = TextListComponent.from(
                    textLines = listOf(
                        Pair(
                            "https://dw2d2daekmyur.cloudfront.net/vote_01.png",
                            RichText.of(
                                "비밀 투표는 반 친구들에 대해 서로 칭찬하는 문화를 만들기 위해 시작되었어요",
                                "#FFF7F7F8",
                                14,
                                "Start",
                                null
                            )
                        ),
                        Pair(
                            "https://dw2d2daekmyur.cloudfront.net/vote_02.png",
                            RichText.of(
                                "비밀 투표함은 매일 밤 23:59에 닫히고 다음날 00:00에 새로운 비밀 투표함이 열려요",
                                "#FFF7F7F8",
                                14,
                                "Start",
                                null
                            )
                        ),
                        Pair(
                            "https://dw2d2daekmyur.cloudfront.net/vote_03.png",
                            RichText.of(
                                "내가 어떤 친구에게 투표했는지는 보이지않으니 안심하세요",
                                "#FFF7F7F8",
                                14,
                                "Start",
                                null
                            )
                        ),
                    )
                ),
                buttonsComponent = ButtonsComponent.of(
                    listOf(
                        ButtonComponent.ofWithClickActionType(
                            RichText.of("이해했어요", "#FF1B1C1E", 16, "Center", "SemiBold"),
                            "#FFF6FE8B",
                            "#FFC0C66B",
                            OnClickActionType.ACTION,
                            Paddings.of(top = 8, bottom = 8)
                        )
                    ),
                    Paddings.of(start = 20, end = 20, bottom = 12, top = 12)
                )
            )
        }

        fun createMessageComponent(): OnBoardingExplanationComponent {
            return OnBoardingExplanationComponent(
                textComponent = TextComponent.of(
                    RichText.of("쪽지에 대해 알려드릴게요", "#FFF7F7F8", 20, "Center", "Bold"),
                    Paddings.of(null, null, bottom = 7, null)
                ),
                textListComponent = TextListComponent.from(
                    textLines = listOf(
                        Pair(
                            "https://dw2d2daekmyur.cloudfront.net/message_01.png",
                            RichText.of(
                                "학교와 학년에 상관없이 고마운 친구, 관심있는 친구에게 내 마음을 전할 수 있어요",
                                "#FFF7F7F8",
                                14,
                                "Start",
                                null
                            )
                        ),
                        Pair(
                            "https://dw2d2daekmyur.cloudfront.net/message_02.png",
                            RichText.of(
                                "마음함은 매일 17:00에 열리고 22:00에 닫혀요 서로의 마음은 22:00에 한꺼번에 전달돼요",
                                "#FFF7F7F8",
                                14,
                                "Start",
                                null
                            )
                        ),
                        Pair(
                            "https://dw2d2daekmyur.cloudfront.net/message_03.png",
                            RichText.of(
                                "익명 쪽지로 내가 보냈다는 사실을 숨길 수 있어요 원한다면 설명을 드러낼 수도 있어요",
                                "#FFF7F7F8",
                                14,
                                "Start",
                                null
                            )
                        ),
                    )
                ),
                buttonsComponent = ButtonsComponent.of(
                    listOf(
                        ButtonComponent.ofWithClickActionType(
                            RichText.of("이해했어요", "#FF1B1C1E", 16, "Center", "SemiBold"),
                            "#FFF6FE8B",
                            "#FFC0C66B",
                            OnClickActionType.ACTION,
                            Paddings.of(top = 8, bottom = 8)
                        )
                    ),
                    Paddings.of(start = 20, end = 20, bottom = 12, top = 12)
                )
            )
        }

    }

}

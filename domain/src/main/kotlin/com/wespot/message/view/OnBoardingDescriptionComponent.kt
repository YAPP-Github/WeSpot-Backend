package com.wespot.message.view

import com.wespot.common.view.ButtonComponent
import com.wespot.common.view.TextLinesComponent
import com.wespot.common.view.TitleComponent

data class OnBoardingDescriptionComponent(
    val titleComponent: TitleComponent,
    val textLinesComponent: TextLinesComponent,
    val buttonComponent: ButtonComponent
) {

    companion object {

        fun createBasicComponent(): OnBoardingDescriptionComponent {
            return OnBoardingDescriptionComponent(
                titleComponent = TitleComponent.from("마음에 대해 알려드릴께요"),
                textLinesComponent = TextLinesComponent.from(
                    textLines = listOf(
                        Pair("https://google.com", "학년과 상관없이 투표해요"),
                        Pair("https://google.com", "지금 시작해보세요"),
                    )
                ),
                buttonComponent = ButtonComponent.from("확인해볼래요")
            )
        }

    }

}

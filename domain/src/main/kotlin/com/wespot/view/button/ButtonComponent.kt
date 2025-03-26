package com.wespot.view.button

import com.wespot.view.button.link.DeepLink
import com.wespot.view.color.StringColor
import com.wespot.view.text.RichText

data class ButtonComponent(
    val richText: RichText,
    val buttonColor: StringColor,
    val pressColor: StringColor,
    val onClickAction: OnClickAction
) {

    companion object {

        fun of(
            richText: RichText,
            buttonColor: String,
            pressColor: String,
        ): ButtonComponent {
            return ButtonComponent(
                richText,
                StringColor.from(buttonColor),
                StringColor.from(pressColor),
                OnClickAction.fromWithType("BackNavigation")
            )
        }

        fun ofWithClickActionType(
            richText: RichText,
            buttonColor: String,
            pressColor: String,
            onClickActionType: OnClickActionType
        ): ButtonComponent {
            return ButtonComponent(
                richText,
                StringColor.from(buttonColor),
                StringColor.from(pressColor),
                OnClickAction.fromWithType(onClickActionType)
            )
        }

        fun ofWithDeepLink(
            richText: RichText,
            buttonColor: String,
            pressColor: String,
            deepLink: DeepLink
        ): ButtonComponent {
            return ButtonComponent(
                richText,
                StringColor.from(buttonColor),
                StringColor.from(pressColor),
                OnClickAction.of(deepLink)
            )
        }

    }
}

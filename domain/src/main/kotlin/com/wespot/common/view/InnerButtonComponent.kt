package com.wespot.common.view

import com.wespot.common.link.DeepLink

data class InnerButtonComponent(
    val text: String,
    val textColor: String,
    val buttonColor: String,
    val pressColor: String,
    val onClickAction: OnClickAction
) {

    companion object {

        fun of(
            text: String,
            textColor: String,
            buttonColor: String,
            pressColor: String,
            onClickActionType: OnClickActionType,
            deepLink: DeepLink
        ): InnerButtonComponent {
            return InnerButtonComponent(
                text,
                textColor,
                buttonColor,
                pressColor,
                OnClickAction.of(onClickActionType, deepLink)
            )
        }

    }

}

package com.wespot.view.button

import com.wespot.view.button.link.DeepLink

data class OnClickAction(
    val type: String,
    val deepLink: DeepLink
) {

    companion object {

        fun of(onClickActionType: OnClickActionType, deepLink: DeepLink): OnClickAction {
            if (onClickActionType == OnClickActionType.NONE) {
                return OnClickAction(onClickActionType.type, DeepLink.NONE)
            }
            return OnClickAction(
                onClickActionType.type,
                deepLink
            )
        }

        fun fromWithType(type: String): OnClickAction {
            return OnClickAction(type, DeepLink.NONE)
        }

    }

}

package com.wespot.view.button

import com.wespot.view.button.link.DeepLink

data class OnClickAction(
    val type: String,
    val deepLink: DeepLink
) {

    companion object {

        private const val DEEP_LINK_TYPE = "deepLinkNavigation"

        fun of(onClickActionType: OnClickActionType, deepLink: DeepLink): OnClickAction {
            if (onClickActionType == OnClickActionType.NONE) {
                return OnClickAction(onClickActionType.type, DeepLink.NONE)
            }
            return OnClickAction(
                onClickActionType.type,
                deepLink
            )
        }

        fun of(deepLink: DeepLink): OnClickAction {
            return OnClickAction(
                DEEP_LINK_TYPE,
                deepLink
            )
        }

        fun fromWithType(type: String): OnClickAction {
            return OnClickAction(type, DeepLink.NONE)
        }

    }

}

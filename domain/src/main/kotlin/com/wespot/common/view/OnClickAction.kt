package com.wespot.common.view

import com.wespot.common.link.DeepLink

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

    }

}

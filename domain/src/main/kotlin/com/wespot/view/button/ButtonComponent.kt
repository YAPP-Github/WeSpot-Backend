package com.wespot.view.button

import com.wespot.view.button.link.DeepLink

data class ButtonComponent(
    val type: String,
    val text: String,
    val link: String = "",
) {

    companion object {

        private const val TYPE = "buttonComponent"

        fun from(text: String): ButtonComponent {
            return ButtonComponent(
                TYPE,
                text
            )
        }

        fun ofWithDeepLink(text: String, deepLink: DeepLink): ButtonComponent {
            return ButtonComponent(
                TYPE,
                text,
                deepLink.deepLinkURL
            )
        }

    }
}

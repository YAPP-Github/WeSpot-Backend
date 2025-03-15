package com.wespot.view

import com.wespot.common.link.DeepLink

data class ButtonComponent(
    val type: String,
    val text: String,
    val link: String = "",
) {

    companion object {

        private const val TYPE = "buttonComponent"

        fun from(text: String): com.wespot.view.ButtonComponent {
            return com.wespot.view.ButtonComponent(
                com.wespot.view.ButtonComponent.Companion.TYPE,
                text
            )
        }

        fun ofWithDeepLink(text: String, deepLink: DeepLink): com.wespot.view.ButtonComponent {
            return com.wespot.view.ButtonComponent(
                com.wespot.view.ButtonComponent.Companion.TYPE,
                text,
                deepLink.deepLinkURL
            )
        }

    }
}

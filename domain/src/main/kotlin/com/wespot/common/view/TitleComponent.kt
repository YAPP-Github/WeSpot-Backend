package com.wespot.common.view

class TitleComponent(
    val type: String,
    val text: String
) {

    companion object {

        private const val TYPE = "titleComponent"

        fun from(text: String): TitleComponent {
            return TitleComponent(TYPE, text)
        }

    }
}

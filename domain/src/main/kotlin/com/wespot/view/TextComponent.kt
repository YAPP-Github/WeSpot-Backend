package com.wespot.view

data class TextComponent(
    val type: String,
    val text: String
) {

    companion object {

        private const val TYPE = "textComponent"

        fun from(text: String): TextComponent {
            return TextComponent(TYPE, text)
        }

    }

}

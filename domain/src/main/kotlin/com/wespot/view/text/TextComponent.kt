package com.wespot.view.text

import com.wespot.view.padding.Paddings

data class TextComponent(
    val type: String,
    val content: TextContent
) {

    companion object {

        private const val TYPE = "textComponent"

        fun of(richText: RichText, paddings: Paddings): TextComponent {
            return TextComponent(
                TYPE,
                TextContent.of(richText, paddings)
            )
        }

    }

}

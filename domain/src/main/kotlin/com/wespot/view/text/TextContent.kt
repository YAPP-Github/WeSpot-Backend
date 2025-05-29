package com.wespot.view.text

import com.wespot.view.padding.Paddings

data class TextContent(
    val richText: RichText,
    val paddings: Paddings
) {

    companion object {

        fun of(richText: RichText, paddings: Paddings): TextContent {
            return TextContent(
                richText,
                paddings
            )
        }

    }

}

package com.wespot.view.text

import com.wespot.view.color.StringColor

data class RichText(
    val text: String,
    val color: StringColor,
    val fontSize: Int,
    val align: Align,
    val fontWeight: String?
) {

    companion object {
        fun of(
            text: String,
            color: String,
            fontSize: Int,
            align: String,
            fontWeight: String?
        ): RichText {
            return RichText(text, StringColor.from(color), fontSize, Align.from(align), fontWeight)
        }
    }

}

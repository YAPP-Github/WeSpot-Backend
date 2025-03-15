package com.wespot.view.text

import com.wespot.view.color.StringColor

data class RichText(
    val text: String,
    val color: StringColor,
    val fontSize: Int,
    val align: Align,
    val fontWeight: String
) {

}

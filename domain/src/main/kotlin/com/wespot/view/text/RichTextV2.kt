package com.wespot.view.text

import com.wespot.view.color.Color

data class RichTextV2(
    val text: String,
    val color: Color = Color.DEFAULT_COLOR,
    val typography: String = "Body01",
    val maxLine: Int? = null,
) {

    companion object {
        val HOT_POST_TEXT: RichTextV2 = RichTextV2(
            text = "Hot Post",
            maxLine = 1
        )
    }

}

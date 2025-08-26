package com.wespot.common.dto.view

import com.fasterxml.jackson.annotation.JsonInclude
import com.wespot.view.color.Color
import com.wespot.view.text.RichTextV2

@JsonInclude(JsonInclude.Include.NON_NULL)
data class RichTextV2Response(
    val text: String,
    val color: Color,
    val typography: String,
    val maxLine: Int? = null,
) {

    companion object {
        fun from(richText: RichTextV2): RichTextV2Response {
            return RichTextV2Response(
                text = richText.text,
                color = richText.color,
                typography = richText.typography,
                maxLine = richText.maxLine
            )
        }
    }

}

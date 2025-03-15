package com.wespot.common.dto.view

import com.fasterxml.jackson.annotation.JsonInclude
import com.wespot.view.text.RichText

@JsonInclude(JsonInclude.Include.NON_NULL)
data class RichTextResponse(
    val text: String,
    val color: String,
    val fontSize: Int,
    val align: String,
    val fontWeight: String
) {

    companion object {

        fun from(richText: RichText): RichTextResponse {
            return RichTextResponse(
                richText.text,
                richText.color.value,
                richText.fontSize,
                richText.align.value,
                richText.fontWeight
            )
        }
    }

}

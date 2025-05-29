package com.wespot.common.dto.view

import com.fasterxml.jackson.annotation.JsonInclude
import com.wespot.view.text.TextComponent
import com.wespot.view.text.TextContent

@JsonInclude(JsonInclude.Include.NON_NULL)
data class TextComponentResponse(
    val type: String,
    val content: TextContentResponse
) {

    data class TextContentResponse(
        val richText: RichTextResponse,
        val paddings: PaddingsResponse
    ) {

        companion object {

            fun from(textContent: TextContent): TextContentResponse {
                return TextContentResponse(
                    richText = RichTextResponse.from(textContent.richText),
                    paddings = PaddingsResponse.from(textContent.paddings)
                )
            }
        }

    }

    companion object {
        fun from(firstTextComponent: TextComponent): TextComponentResponse {
            return TextComponentResponse(
                firstTextComponent.type,
                TextContentResponse.from(firstTextComponent.content)
            )
        }
    }

}

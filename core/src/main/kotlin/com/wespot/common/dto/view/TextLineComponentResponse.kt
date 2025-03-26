package com.wespot.common.dto.view

import com.wespot.view.text.TextLineComponent

data class TextLineComponentResponse(
    val icon: String,
    val richText: RichTextResponse,
) {

    companion object {

        fun from(textLineComponent: TextLineComponent): TextLineComponentResponse {
            return TextLineComponentResponse(
                textLineComponent.icon,
                RichTextResponse.from(textLineComponent.richText)
            )
        }

    }

}

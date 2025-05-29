package com.wespot.common.dto.view

import com.wespot.view.text.TextListComponent

data class TextListComponentResponse(
    val type: String,
    val textList: List<TextLineComponentResponse>
) {

    companion object {

        private const val TYPE = "textListComponent"

        fun from(textListComponent: TextListComponent): Any {
            return TextListComponentResponse(
                TYPE,
                textListComponent.textList.map { TextLineComponentResponse.from(it) }
            )
        }

    }


}

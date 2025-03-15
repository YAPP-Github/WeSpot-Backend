package com.wespot.common.dto.view

import com.fasterxml.jackson.annotation.JsonInclude
import com.wespot.view.text.ChipComponent
import com.wespot.view.text.ChipContent

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ChipComponentResponse(
    val type: String,
    val content: ChipContentResponse
) {

    data class ChipContentResponse(
        val richText: RichTextResponse,
        val containerColor: String,
        val borderColor: String,
        val paddings: PaddingsResponse
    ) {

        companion object {
            fun from(chipContent: ChipContent): ChipContentResponse {
                return ChipContentResponse(
                    richText = RichTextResponse.from(chipContent.richText),
                    containerColor = chipContent.containerColor.value,
                    borderColor = chipContent.borderColor.value,
                    paddings = PaddingsResponse.from(chipContent.paddings)
                )
            }
        }

    }

    companion object {
        fun from(chipComponent: ChipComponent): ChipComponentResponse {
            return ChipComponentResponse(
                chipComponent.type,
                ChipContentResponse.from(chipComponent.content)
            )
        }
    }

}


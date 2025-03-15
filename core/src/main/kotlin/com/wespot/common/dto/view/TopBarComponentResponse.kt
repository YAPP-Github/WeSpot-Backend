package com.wespot.common.dto.view

import com.fasterxml.jackson.annotation.JsonInclude
import com.wespot.view.TopBarComponent
import com.wespot.view.TopBarContent

@JsonInclude(JsonInclude.Include.NON_NULL)
data class TopBarComponentResponse(
    val type: String,
    val content: TopBarContentResponse
) {

    data class TopBarContentResponse(
        val richText: RichTextResponse,
        val icons: List<IconResponse>
    ) {

        companion object {
            fun from(topBarContent: TopBarContent): TopBarContentResponse {
                return TopBarContentResponse(
                    richText = RichTextResponse.from(topBarContent.richText),
                    topBarContent.icons
                        .values
                        .map { IconResponse.from(it) }
                )
            }
        }

    }

    companion object {
        fun from(topBarComponent: TopBarComponent): TopBarComponentResponse {
            return TopBarComponentResponse(
                topBarComponent.type,
                TopBarContentResponse.from(topBarComponent.content)
            )
        }
    }

}

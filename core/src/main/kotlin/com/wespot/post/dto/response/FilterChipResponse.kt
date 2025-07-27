package com.wespot.post.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.wespot.common.dto.view.IconV2Response
import com.wespot.common.dto.view.RichTextV2Response
import com.wespot.view.chip.FilterChip

@JsonInclude(JsonInclude.Include.NON_NULL)
data class FilterChipResponse(
    val type: String,
    val content: FilterChipContentResponse,
) {

    data class FilterChipContentResponse(
        val id: Long,
        val icon: IconV2Response,
        val text: RichTextV2Response,
        val target: String,
    )

    companion object {
        fun from(filterChip: FilterChip): FilterChipResponse {
            return FilterChipResponse(
                type = filterChip.type,
                content = FilterChipContentResponse(
                    id = filterChip.content.id,
                    icon = IconV2Response.from(filterChip.content.icon),
                    text = RichTextV2Response.from(filterChip.content.text),
                    target = filterChip.content.target
                )
            )
        }
    }
}

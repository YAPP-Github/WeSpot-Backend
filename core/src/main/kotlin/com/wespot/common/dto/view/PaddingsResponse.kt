package com.wespot.common.dto.view

import com.fasterxml.jackson.annotation.JsonInclude
import com.wespot.view.padding.Paddings

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PaddingsResponse(
    val start: Int?,
    val end: Int?,
    val bottom: Int?
) {
    companion object {
        fun from(paddings: Paddings): PaddingsResponse {
            return PaddingsResponse(paddings.start, paddings.end, paddings.bottom)
        }
    }
}

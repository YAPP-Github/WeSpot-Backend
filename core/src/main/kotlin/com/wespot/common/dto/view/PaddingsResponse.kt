package com.wespot.common.dto.view

import com.fasterxml.jackson.annotation.JsonInclude
import com.wespot.view.padding.Paddings
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PaddingsResponse(
    val start: Int? = null,
    val end: Int? = null,
    val bottom: Int? = null,
    val top: Int? = null
) {
    companion object {
        fun from(paddings: Paddings?): PaddingsResponse {
            if (Objects.isNull(paddings)) {
                return PaddingsResponse()
            }

            return PaddingsResponse(paddings!!.start, paddings.end, paddings.bottom, paddings.top)
        }
    }
}

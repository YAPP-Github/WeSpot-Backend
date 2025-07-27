package com.wespot.common.dto.view

import com.fasterxml.jackson.annotation.JsonInclude
import com.wespot.view.color.Color

@JsonInclude(JsonInclude.Include.NON_NULL)
class ColorResponse(
    val value: String,
    val type: String,
) {

    companion object {

        fun from(color: Color): ColorResponse {
            return ColorResponse(
                value = color.value,
                type = color.type
            )
        }

    }

}

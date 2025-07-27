package com.wespot.common.dto.view

import com.fasterxml.jackson.annotation.JsonInclude
import com.wespot.view.icon.IconV2

@JsonInclude(JsonInclude.Include.NON_NULL)
data class IconV2Response(
    val url: String,
    val color: ColorResponse
) {

    companion object {

        fun from(icon: IconV2): IconV2Response {
            return IconV2Response(
                url = icon.url,
                color = ColorResponse.from(icon.color)
            )
        }

    }

}

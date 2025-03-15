package com.wespot.common.dto.view

import com.fasterxml.jackson.annotation.JsonInclude
import com.wespot.view.icon.Icon

@JsonInclude(JsonInclude.Include.NON_NULL)
data class IconResponse(
    val url: String,
    val width: Int,
    val height: Int,
    val onClickAction: OnClickActionResponse
) {

    companion object {

        fun from(icon: Icon): IconResponse {
            return IconResponse(
                icon.url,
                icon.width,
                icon.height,
                OnClickActionResponse.from(icon.onClickAction)
            )
        }

    }

}

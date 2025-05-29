package com.wespot.common.dto.view

import com.fasterxml.jackson.annotation.JsonInclude
import com.wespot.view.button.OnClickAction

@JsonInclude(JsonInclude.Include.NON_NULL)
data class OnClickActionResponse(
    val type: String,
    val deepLink: String?
) {

    companion object {
        fun from(onClickAction: OnClickAction): OnClickActionResponse {
            return OnClickActionResponse(
                onClickAction.type,
                onClickAction.deepLink.deepLinkURL
            )
        }
    }

}

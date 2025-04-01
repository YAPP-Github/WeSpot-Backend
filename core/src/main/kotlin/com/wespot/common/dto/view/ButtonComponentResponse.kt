package com.wespot.common.dto.view

import com.fasterxml.jackson.annotation.JsonInclude
import com.wespot.view.button.ButtonComponent

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ButtonComponentResponse(
    val richText: RichTextResponse,
    val buttonColor: String,
    val pressColor: String,
    val onClickAction: OnClickActionResponse,
    val paddings: PaddingsResponse?
) {

    companion object {
        fun from(buttonComponent: ButtonComponent): ButtonComponentResponse {
            return ButtonComponentResponse(
                RichTextResponse.from(buttonComponent.richText),
                buttonComponent.buttonColor.value,
                buttonComponent.pressColor.value,
                OnClickActionResponse.from(buttonComponent.onClickAction),
                PaddingsResponse.from(buttonComponent.paddings)
            )
        }
    }

}

package com.wespot.common.dto.view

import com.fasterxml.jackson.annotation.JsonInclude
import com.wespot.view.button.ButtonsComponent
import com.wespot.view.button.ButtonsContent

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ButtonsComponentResponse(
    val type: String,
    val content: ButtonsContentResponse
) {

    data class ButtonsContentResponse(
        val buttons: List<ButtonComponentResponse>,
        val paddings: PaddingsResponse
    ) {

        companion object {
            fun from(buttonsContent: ButtonsContent): ButtonsContentResponse {
                return ButtonsContentResponse(
                    buttonsContent.buttons
                        .map { ButtonComponentResponse.from(it) },
                    PaddingsResponse.from(buttonsContent.paddings)
                )
            }
        }

    }

    companion object {
        fun from(buttonsComponent: ButtonsComponent): ButtonsComponentResponse {
            return ButtonsComponentResponse(
                buttonsComponent.type,
                ButtonsContentResponse.from(buttonsComponent.content)
            )
        }
    }

}

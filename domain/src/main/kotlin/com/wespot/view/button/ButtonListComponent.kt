package com.wespot.view.button

import com.wespot.view.padding.Paddings

data class ButtonListComponent(
    val type: String,
    val content: ButtonsContent,
) {

    companion object {

        const val TYPE = "buttonsComponent"

        fun from(buttons: List<ButtonComponent>, paddings: Paddings): ButtonListComponent {
            return ButtonListComponent(
                TYPE,
                ButtonsContent.of(buttons, paddings)
            )
        }

    }

}

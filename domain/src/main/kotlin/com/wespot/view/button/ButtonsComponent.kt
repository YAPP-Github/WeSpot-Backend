package com.wespot.view.button

import com.wespot.view.padding.Paddings

data class ButtonsComponent(
    val type: String,
    val content: ButtonsContent,
) {

    companion object {

        const val TYPE = "buttonsComponent"

        fun of(buttons: List<ButtonComponent>, paddings: Paddings): ButtonsComponent {
            return ButtonsComponent(
                TYPE,
                ButtonsContent.of(buttons, paddings)
            )
        }

    }

}

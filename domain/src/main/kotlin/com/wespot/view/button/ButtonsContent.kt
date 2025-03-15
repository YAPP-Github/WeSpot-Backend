package com.wespot.view.button

import com.wespot.view.padding.Paddings

data class ButtonsContent(
    val buttons: List<ButtonComponent>,
    val paddings: Paddings
) {
    companion object {

        fun of(buttons: List<ButtonComponent>, paddings: Paddings): ButtonsContent {
            return ButtonsContent(buttons, paddings)
        }

    }

}

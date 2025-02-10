package com.wespot.common.view

data class ButtonListComponent(
    val buttons: List<InnerButtonComponent>
) {

    companion object {

        const val TYPE = "buttonListComponent"

        fun from(buttons: List<InnerButtonComponent>): ButtonListComponent {
            return ButtonListComponent(buttons)
        }

    }

}

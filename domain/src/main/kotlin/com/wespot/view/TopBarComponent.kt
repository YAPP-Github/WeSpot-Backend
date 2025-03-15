package com.wespot.view

data class TopBarComponent(
    val type: String,
    val text: String,
) {

    companion object {

        const val TYPE = "topBarComponent"

        fun from(text: String): TopBarComponent {
            return TopBarComponent(TYPE, text)
        }

    }

}

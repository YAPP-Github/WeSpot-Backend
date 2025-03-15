package com.wespot.view

data class SubTitleComponent(
    val type: String,
    val text: String,
) {

    companion object {

        const val TYPE = "subTitleComponent"

        fun from(text: String): SubTitleComponent {
            return SubTitleComponent(TYPE, text)
        }

    }

}

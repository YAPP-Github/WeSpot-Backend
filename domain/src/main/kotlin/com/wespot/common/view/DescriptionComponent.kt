package com.wespot.common.view

data class DescriptionComponent(
    val type: String,
    val text: String
) {

    companion object {

        const val TYPE = "descriptionComponent"

        fun from(text: String): DescriptionComponent {
            return DescriptionComponent(TYPE, text)
        }

    }

}

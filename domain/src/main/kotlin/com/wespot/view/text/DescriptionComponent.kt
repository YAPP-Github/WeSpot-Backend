package com.wespot.view.text

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

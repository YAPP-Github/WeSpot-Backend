package com.wespot.view.text

data class ChipComponent(
    val type: String,
    val text: String
) {

    companion object {

        const val TYPE = "chipComponent"

        fun from(text: String): ChipComponent {
            return ChipComponent(TYPE, text)
        }

    }

}

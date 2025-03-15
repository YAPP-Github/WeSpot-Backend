package com.wespot.view.text

data class TextLineComponent(
    val icon: String,
    val text: String
) {

    companion object {

        fun of(icon: String, text: String): TextLineComponent {
            return TextLineComponent(icon, text)
        }

    }

}

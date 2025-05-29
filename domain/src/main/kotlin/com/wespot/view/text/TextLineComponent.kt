package com.wespot.view.text

data class TextLineComponent(
    val icon: String,
    val richText: RichText
) {

    companion object {

        fun of(icon: String, text: RichText): TextLineComponent {
            return TextLineComponent(icon, text)
        }

    }

}

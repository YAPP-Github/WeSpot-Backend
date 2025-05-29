package com.wespot.view.image

data class DescriptionImageComponent(
    val type: String,
    val url: String,
    val width: Int,
    val height: Int
) {

    companion object {

        const val TYPE = "descriptionImageComponent"

        fun of(url: String, width: Int, height: Int): DescriptionImageComponent {
            return DescriptionImageComponent(TYPE, url, width, height)
        }

    }

}

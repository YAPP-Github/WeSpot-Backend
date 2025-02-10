package com.wespot.common.view

data class DescriptionImageComponent(
    val type: String,
    val url: String,
    val width: String,
    val height: String
) {

    companion object {

        const val TYPE = "descriptionImageComponent"

        fun of(url: String, width: Int, height: Int): DescriptionImageComponent {
            return DescriptionImageComponent(TYPE, url, width.toString(), height.toString())
        }

    }

}

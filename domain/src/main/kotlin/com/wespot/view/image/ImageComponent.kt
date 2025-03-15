package com.wespot.view.image

data class ImageComponent(
    val type: String,
    val url: String,
    val width: Int,
    val height: Int,
) {

    companion object {

        private const val TYPE = "imageComponent"

        fun of(url: String, width: Int, height: Int): ImageComponent {
            return ImageComponent(TYPE, url, width, height)
        }

        fun fromSameSizeAsParentComponent(url: String): ImageComponent {
            return ImageComponent(TYPE, url, 0, 0)
        }

    }

}

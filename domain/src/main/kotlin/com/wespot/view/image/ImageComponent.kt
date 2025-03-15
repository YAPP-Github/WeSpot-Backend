package com.wespot.view.image

import com.wespot.view.padding.Paddings

data class ImageComponent(
    val type: String,
    val imageContent: ImageContent,
) {

    companion object {

        private const val TYPE = "imageComponent"

        fun of(url: String, width: Int, height: Int, paddings: Paddings): ImageComponent {
            return ImageComponent(
                TYPE,
                ImageContent.of(url, width, height, paddings)
            )
        }

    }

}

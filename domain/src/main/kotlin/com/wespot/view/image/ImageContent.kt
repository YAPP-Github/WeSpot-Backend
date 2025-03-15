package com.wespot.view.image

import com.wespot.view.padding.Paddings

data class ImageContent(
    val url: String,
    val width: Int,
    val height: Int,
    val paddings: Paddings
) {

    companion object {
        fun of(url: String, width: Int, height: Int, paddings: Paddings): ImageContent {
            return ImageContent(
                url,
                width,
                height,
                paddings
            )
        }
    }

}

package com.wespot.common.dto.view

import com.wespot.view.image.ImageContentV2

data class ImageContentV2Response(
    val url: String,
    val width: Int,
    val height: Int,
) {

    companion object {

        fun from(image: ImageContentV2): ImageContentV2Response {
            return ImageContentV2Response(
                url = image.url,
                width = image.width,
                height = image.height
            )
        }

    }


}

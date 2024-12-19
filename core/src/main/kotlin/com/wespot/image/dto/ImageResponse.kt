package com.wespot.image.dto

import com.wespot.image.Image

class ImageResponse(
    val id: Long,
    val url: String,
) {

    companion object {

        fun of(
            id: Long, image: Image
        ) = ImageResponse(id = id, url = image.url)

    }

}

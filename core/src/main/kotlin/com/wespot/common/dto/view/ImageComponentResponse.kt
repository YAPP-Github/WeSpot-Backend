package com.wespot.common.dto.view

import com.fasterxml.jackson.annotation.JsonInclude
import com.wespot.view.image.ImageComponent
import com.wespot.view.image.ImageContent

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ImageComponentResponse(
    val type: String,
    val content: ImageContentResponse
) {

    data class ImageContentResponse(
        val url: String,
        val width: Int,
        val height: Int,
        val paddings: PaddingsResponse
    ) {

        companion object {
            fun from(imageContent: ImageContent): ImageContentResponse {
                return ImageContentResponse(
                    imageContent.url,
                    imageContent.width,
                    imageContent.height,
                    PaddingsResponse.from(imageContent.paddings)
                )
            }
        }
    }

    companion object {
        fun from(imageComponent: ImageComponent): ImageComponentResponse {
            return ImageComponentResponse(
                imageComponent.type,
                ImageContentResponse.from(imageComponent.content)
            )
        }
    }

}

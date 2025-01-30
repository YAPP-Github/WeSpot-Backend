package com.wespot.common.dto

import com.wespot.common.view.ButtonComponent
import com.wespot.common.view.ImageComponent
import com.wespot.common.view.TitleComponent
import com.wespot.common.view.UpdatedModalComponent

data class UpdatedModalComponentResponse(
    val id: Long,
    val name: String,
    val data: List<Any>
) {

    data class TitleComponentResponse(
        val type: String,
        val text: String,
    ) {

        companion object {

            fun from(titleComponent: TitleComponent): TitleComponentResponse {
                return TitleComponentResponse(
                    titleComponent.type,
                    titleComponent.text
                )
            }

        }

    }


    data class ImageComponentResponse(
        val type: String,
        val url: String,
        val width: Int,
        val height: Int
    ) {

        companion object {
            fun from(imageComponent: ImageComponent): ImageComponentResponse {
                return ImageComponentResponse(
                    imageComponent.type,
                    imageComponent.url,
                    imageComponent.width,
                    imageComponent.height
                )
            }
        }

    }

    data class ButtonComponentResponse(
        val type: String,
        val text: String
    ) {

        companion object {
            fun from(buttonComponent: ButtonComponent): ButtonComponentResponse {
                return ButtonComponentResponse(buttonComponent.type, buttonComponent.text)
            }
        }

    }

    data class ButtonComponentWithLinkResponse(
        val type: String,
        val text: String,
        val link: String
    ) {

        companion object {
            fun from(buttonComponent: ButtonComponent): ButtonComponentWithLinkResponse {
                return ButtonComponentWithLinkResponse(
                    buttonComponent.type,
                    buttonComponent.text,
                    buttonComponent.link
                )
            }
        }

    }

    companion object {
        fun from(updatedModalComponent: UpdatedModalComponent): UpdatedModalComponentResponse {
            return UpdatedModalComponentResponse(
                id = 1,
                name = updatedModalComponent.type,
                data = listOf(
                    TitleComponentResponse.from(updatedModalComponent.titleComponent),
                    ImageComponentResponse.from(updatedModalComponent.imageComponent),
                    ButtonComponentResponse.from(updatedModalComponent.skipButtonComponent),
                    ButtonComponentWithLinkResponse.from(updatedModalComponent.moveToUpdatedFeatureViewButtonComponent)
                )
            )
        }
    }

}

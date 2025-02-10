package com.wespot.common.dto

import com.wespot.common.view.*
import com.wespot.common.view.update.UpdatedModalComponent

data class UpdatedModalComponentResponse(
    val id: Long,
    val name: String,
    val data: List<Any>
) {

    data class TextComponentResponse(
        val type: String,
        val text: String,
    ) {

        companion object {

            fun from(textComponent: TextComponent): TextComponentResponse {
                return TextComponentResponse(
                    textComponent.type,
                    textComponent.text
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
                    TextComponentResponse.from(updatedModalComponent.textComponent),
                    ImageComponentResponse.from(updatedModalComponent.imageComponent),
                    ButtonComponentResponse.from(updatedModalComponent.skipButtonComponent),
                    ButtonComponentWithLinkResponse.from(updatedModalComponent.moveToUpdatedFeatureViewButtonComponent)
                )
            )
        }
    }

}

package com.wespot.message.dto.response.view

import com.wespot.common.view.*
import com.wespot.message.view.*

data class MessageOnBoardingResponse(
    val id: Long,
    val name: String,
    val data: List<MessageOnBoardingPageResponse>
) {

    data class MessageOnBoardingPageResponse(
        val page: Int,
        val data: List<Any>
    ) {

        data class TitleComponentResponse(
            val type: String,
            val text: String
        ) {

            companion object {

                fun from(titleComponent: TitleComponent): TitleComponentResponse {
                    return TitleComponentResponse(titleComponent.type, titleComponent.text)
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

        data class TextLinesComponentResponse(
            val type: String,
            val textList: List<TextLineComponentResponse>
        ) {

            companion object {

                fun from(textLinesComponent: TextLinesComponent): TextLinesComponentResponse {
                    return TextLinesComponentResponse(
                        textLinesComponent.type,
                        textLinesComponent.textLines.map { TextLineComponentResponse.of(it) }
                    )
                }

            }

        }

        data class TextLineComponentResponse(
            val icon: String,
            val text: String
        ) {
            companion object {

                fun of(textLineComponent: TextLineComponent): TextLineComponentResponse {
                    return TextLineComponentResponse(textLineComponent.icon, textLineComponent.text)
                }

            }
        }

        companion object {
            fun fromWithImageComponent(
                page: Int,
                data: OnBoardingImageComponent
            ): MessageOnBoardingPageResponse {
                return MessageOnBoardingPageResponse(
                    page = page,
                    data = listOf(
                        TitleComponentResponse.from(data.titleComponent),
                        ImageComponentResponse.from(data.imageComponent),
                        ButtonComponentResponse.from(data.buttonComponent)
                    )
                )
            }

            fun fromWithDescriptionComponent(
                page: Int,
                data: OnBoardingDescriptionComponent
            ): MessageOnBoardingPageResponse {
                return MessageOnBoardingPageResponse(
                    page = page,
                    data = listOf(
                        TitleComponentResponse.from(data.titleComponent),
                        TextLinesComponentResponse.from(data.textLinesComponent),
                        ButtonComponentResponse.from(data.buttonComponent)
                    )
                )
            }
        }

    }

    companion object {
        fun from(onBoardingBottomSheetComponent: OnBoardingBottomSheetComponent): MessageOnBoardingResponse {
            return MessageOnBoardingResponse(
                id = 1,
                name = onBoardingBottomSheetComponent.name,
                data = listOf(
                    MessageOnBoardingPageResponse.fromWithImageComponent(
                        page = 1,
                        data = onBoardingBottomSheetComponent.onBoardingImageComponent
                    ),
                    MessageOnBoardingPageResponse.fromWithDescriptionComponent(
                        page = 2,
                        data = onBoardingBottomSheetComponent.onBoardingDescriptionComponent
                    )
                )
            )
        }
    }

}

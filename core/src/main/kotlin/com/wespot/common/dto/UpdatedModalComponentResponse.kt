package com.wespot.common.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.wespot.common.link.DeepLink
import com.wespot.common.view.*
import com.wespot.view.update.UpdatedModalComponent

data class UpdatedModalComponentResponse(
    val id: Long,
    val name: String,
    val data: List<Any>
) {

    data class TopBarComponentResponse(
        val type: String,
        val text: String
    ) {

        companion object {

            fun from(topBarComponent: TopBarComponent): TopBarComponentResponse {
                return TopBarComponentResponse(topBarComponent.type, topBarComponent.text)
            }

        }

    }

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

    data class SubTitleComponentResponse(
        val type: String,
        val text: String
    ) {

        companion object {

            fun from(subTitleComponent: SubTitleComponent): SubTitleComponentResponse {
                return SubTitleComponentResponse(subTitleComponent.type, subTitleComponent.text)
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

    data class ChipComponentResponse(
        val type: String,
        val text: String
    ) {

        companion object {

            fun from(chipComponent: ChipComponent): ChipComponentResponse {
                return ChipComponentResponse(chipComponent.type, chipComponent.text)
            }

        }

    }

    data class DescriptionComponentResponse(
        val type: String,
        val text: String
    ) {

        companion object {

            fun from(descriptionComponent: DescriptionComponent): DescriptionComponentResponse {
                return DescriptionComponentResponse(descriptionComponent.type, descriptionComponent.text)
            }

        }

    }

    data class DescriptionImageComponentResponse(
        val type: String,
        val url: String,
        val width: Int,
        val height: Int
    ) {

        companion object {

            fun from(descriptionImageComponent: DescriptionImageComponent): DescriptionImageComponentResponse {
                return DescriptionImageComponentResponse(
                    descriptionImageComponent.type,
                    descriptionImageComponent.url,
                    descriptionImageComponent.width,
                    descriptionImageComponent.height
                )
            }

        }

    }

    data class ButtonListComponentResponse(
        val type: String,
        val buttonList: List<InnerButtonComponentResponse>
    ) {

        companion object {

            fun from(buttonListComponent: ButtonListComponent): ButtonListComponentResponse {
                return ButtonListComponentResponse(
                    type = buttonListComponent.type,
                    buttonList = buttonListComponent.buttons
                        .stream()
                        .map { InnerButtonComponentResponse.from(it) }
                        .toList()
                )
            }

        }

        data class InnerButtonComponentResponse(
            val text: String,
            val textColor: String,
            val buttonColor: String,
            val pressColor: String,
            val onClickAction: OnClickActionResponse,
        ) {

            companion object {

                fun from(innerButtonComponent: InnerButtonComponent): InnerButtonComponentResponse {
                    return InnerButtonComponentResponse(
                        innerButtonComponent.text,
                        innerButtonComponent.textColor,
                        innerButtonComponent.buttonColor,
                        innerButtonComponent.pressColor,
                        OnClickActionResponse.from(innerButtonComponent)
                    )
                }

            }

            @JsonInclude(JsonInclude.Include.NON_NULL)
            data class OnClickActionResponse(
                val type: String?,
                val deepLink: String?
            ) {

                companion object {
                    fun from(innerButtonComponent: InnerButtonComponent): OnClickActionResponse {
                        return OnClickActionResponse(
                            if (innerButtonComponent.onClickAction.type == OnClickActionType.NONE.type) null else innerButtonComponent.onClickAction.type,
                            if (innerButtonComponent.onClickAction.deepLink == DeepLink.NONE) null else innerButtonComponent.onClickAction.deepLink.deepLinkURL
                        )
                    }
                }

            }

        }

    }

    companion object {
        fun from(updatedModalComponent: com.wespot.view.update.UpdatedModalComponent): UpdatedModalComponentResponse {
            return UpdatedModalComponentResponse(
                id = 1,
                name = updatedModalComponent.type,
                data = listOf(
                    TopBarComponentResponse.from(updatedModalComponent.topBarComponent),
                    TitleComponentResponse.from(updatedModalComponent.titleComponent),
                    SubTitleComponentResponse.from(updatedModalComponent.subTitleComponent),
                    ImageComponentResponse.from(updatedModalComponent.imageComponent),
                    ChipComponentResponse.from(updatedModalComponent.chipComponent),
                    DescriptionComponentResponse.from(updatedModalComponent.descriptionComponent),
                    DescriptionImageComponentResponse.from(updatedModalComponent.descriptionImageComponent),
                    ButtonListComponentResponse.from(updatedModalComponent.buttonListComponent),
                )
            )
        }
    }

}

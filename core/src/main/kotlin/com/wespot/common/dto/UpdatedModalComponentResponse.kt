package com.wespot.common.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.wespot.common.dto.view.*
import com.wespot.view.TopBarComponent
import com.wespot.view.button.ButtonsComponent
import com.wespot.view.image.ImageComponent
import com.wespot.view.text.ChipComponent
import com.wespot.view.text.TextComponent

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UpdatedModalComponentResponse(
    val id: Long,
    val name: String,
    val data: List<Any>
) {

    data class ContentSectionResponse(
        val type: String,
        val components: List<Any>
    ) {

        companion object {

            private const val CONTENT_SECTION_TYPE = "contentSection"

            fun of(
                topBarComponent: TopBarComponent,
                firstTextComponent: TextComponent,
                secondTextComponent: TextComponent,
                firstImageComponent: ImageComponent,
                chipComponent: ChipComponent,
                thirdTextComponent: TextComponent,
                secondImageComponent: ImageComponent
            ): ContentSectionResponse {
                return ContentSectionResponse(
                    CONTENT_SECTION_TYPE,
                    listOf(
                        TopBarComponentResponse.from(topBarComponent),
                        TextComponentResponse.from(firstTextComponent),
                        TextComponentResponse.from(secondTextComponent),
                        ImageComponentResponse.from(firstImageComponent),
                        ChipComponentResponse.from(chipComponent),
                        TextComponentResponse.from(thirdTextComponent),
                        ImageComponentResponse.from(secondImageComponent)
                    )
                )
            }

        }

    }

    data class BottomSectionResponse(
        val type: String,
        val components: List<Any>
    ) {

        companion object {

            private const val BOTTOM_SECTION_TYPE = "bottomSection"

            fun from(
                buttonsComponent: ButtonsComponent
            ): BottomSectionResponse {
                return BottomSectionResponse(
                    BOTTOM_SECTION_TYPE,
                    listOf(ButtonsComponentResponse.from(buttonsComponent))
                )
            }

        }

    }

    companion object {
        fun from(updatedModalComponent: com.wespot.view.update.UpdatedModalComponent): UpdatedModalComponentResponse {
            return UpdatedModalComponentResponse(
                id = 1,
                name = updatedModalComponent.type,
                data = listOf(
                    ContentSectionResponse.of(
                        updatedModalComponent.topBarComponent,
                        updatedModalComponent.firstTextComponent,
                        updatedModalComponent.secondTextComponent,
                        updatedModalComponent.firstImageComponent,
                        updatedModalComponent.chipComponent,
                        updatedModalComponent.thirdTextComponent,
                        updatedModalComponent.secondImageComponent,
                    ),
                    BottomSectionResponse.from(updatedModalComponent.buttonsComponent)
                )
            )
        }
    }

}

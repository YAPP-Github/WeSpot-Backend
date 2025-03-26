package com.wespot.common.dto

import com.wespot.common.dto.view.ButtonsComponentResponse
import com.wespot.common.dto.view.ImageComponentResponse
import com.wespot.common.dto.view.TextComponentResponse
import com.wespot.common.dto.view.TextListComponentResponse
import com.wespot.view.OnBoardingBottomSheetComponent

data class OnBoardingResponse(
    val id: Long,
    val name: String,
    val data: List<Any>
) {

    companion object {

        fun fromFirstPage(onBoardingBottomSheetComponent: OnBoardingBottomSheetComponent): OnBoardingResponse {
            return OnBoardingResponse(
                id = 1,
                name = onBoardingBottomSheetComponent.name,
                data = listOf(
                    ContentSectionResponse.from(
                        TextComponentResponse.from(onBoardingBottomSheetComponent.onBoardingWelcomePageComponent.textComponent),
                        ImageComponentResponse.from(onBoardingBottomSheetComponent.onBoardingWelcomePageComponent.imageComponent)
                    ),
                    BottomSectionResponse.from(
                        ButtonsComponentResponse.from(onBoardingBottomSheetComponent.onBoardingWelcomePageComponent.buttonsComponent)
                    ),
                )
            )
        }

        fun fromSecondpage(onBoardingBottomSheetComponent: OnBoardingBottomSheetComponent): OnBoardingResponse {
            return OnBoardingResponse(
                id = 2,
                name = onBoardingBottomSheetComponent.name,
                data = listOf(
                    ContentSectionResponse.from(
                        TextComponentResponse.from(onBoardingBottomSheetComponent.onBoardingExplanationComponent.textComponent),
                        TextListComponentResponse.from(onBoardingBottomSheetComponent.onBoardingExplanationComponent.textListComponent)
                    ),
                    BottomSectionResponse.from(
                        ButtonsComponentResponse.from(onBoardingBottomSheetComponent.onBoardingExplanationComponent.buttonsComponent)
                    )
                )
            )
        }

    }

    data class ContentSectionResponse(
        val type: String,
        val components: List<Any>
    ) {

        companion object {

            private const val TYPE = "contentSection"

            fun from(vararg components: Any): BottomSectionResponse {
                return BottomSectionResponse(
                    TYPE,
                    components.asList()
                )
            }

        }

    }

    data class BottomSectionResponse(
        val type: String,
        val components: List<Any>
    ) {

        companion object {

            private const val TYPE = "bottomSection"

            fun from(vararg components: Any): BottomSectionResponse {
                return BottomSectionResponse(
                    TYPE,
                    components.asList()
                )
            }

        }

    }


}

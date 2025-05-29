package com.wespot.common.dto

import com.wespot.common.dto.view.ButtonsComponentResponse
import com.wespot.common.dto.view.ImageComponentResponse
import com.wespot.common.dto.view.TextComponentResponse
import com.wespot.common.dto.view.TextListComponentResponse
import com.wespot.view.OnBoardingBottomSheetComponent
import com.wespot.view.OnBoardingExplanationComponent
import com.wespot.view.OnBoardingWelcomePageComponent

data class OnBoardingResponse(
    val id: Long,
    val name: String,
    val data: List<Any>
) {

    companion object {

        fun fromFirstPage(
            id: Long = 1,
            onBoardingBottomSheetComponentName: String,
            onBoardingWelcomePageComponent: OnBoardingWelcomePageComponent
        ): OnBoardingResponse {
            return OnBoardingResponse(
                id = id,
                name = onBoardingBottomSheetComponentName,
                data = listOf(
                    ContentSectionResponse.from(
                        TextComponentResponse.from(onBoardingWelcomePageComponent.textComponent),
                        ImageComponentResponse.from(onBoardingWelcomePageComponent.imageComponent)
                    ),
                    BottomSectionResponse.from(
                        ButtonsComponentResponse.from(onBoardingWelcomePageComponent.buttonsComponent)
                    ),
                )
            )
        }

        fun fromSecondPage(
            id: Long = 2,
            onBoardingBottomSheetComponentName: String,
            onBoardingExplanationComponent: OnBoardingExplanationComponent
        ): OnBoardingResponse {
            return OnBoardingResponse(
                id = id,
                name = onBoardingBottomSheetComponentName,
                data = listOf(
                    ContentSectionResponse.from(
                        TextComponentResponse.from(onBoardingExplanationComponent.textComponent),
                        TextListComponentResponse.from(onBoardingExplanationComponent.textListComponent)
                    ),
                    BottomSectionResponse.from(
                        ButtonsComponentResponse.from(onBoardingExplanationComponent.buttonsComponent)
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

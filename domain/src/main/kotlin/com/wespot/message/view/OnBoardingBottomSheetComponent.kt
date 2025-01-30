package com.wespot.message.view

data class OnBoardingBottomSheetComponent(
    val name: String,
    val onBoardingImageComponent: OnBoardingImageComponent,
    val onBoardingDescriptionComponent: OnBoardingDescriptionComponent
) {

    companion object {

        private const val TYPE = "OnBoardingBottomSheet"

        fun create(): OnBoardingBottomSheetComponent {
            return OnBoardingBottomSheetComponent(
                name = TYPE,
                onBoardingImageComponent = OnBoardingImageComponent.createBasicComponent(),
                onBoardingDescriptionComponent = OnBoardingDescriptionComponent.createBasicComponent()
            )
        }

    }

}

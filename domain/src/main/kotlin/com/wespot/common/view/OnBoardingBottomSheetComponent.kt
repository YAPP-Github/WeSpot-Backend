package com.wespot.common.view

data class OnBoardingBottomSheetComponent(
    val name: String,
    val onBoardingImageComponent: OnBoardingImageComponent,
    val onBoardingDescriptionComponent: OnBoardingDescriptionComponent
) {

    companion object {

        private const val TYPE = "OnBoardingBottomSheet"

        fun fromWithCategory(category: String): OnBoardingBottomSheetComponent {
            val onboardingCategory = OnBoardingCategory.from(category)

            return when (onboardingCategory) {
                OnBoardingCategory.MESSAGE -> createMessageType()
                OnBoardingCategory.VOTE -> createVoteType()
            }
        }

        private fun createMessageType(): OnBoardingBottomSheetComponent {
            return OnBoardingBottomSheetComponent(
                name = TYPE,
                onBoardingImageComponent = OnBoardingImageComponent.createMessageComponent(),
                onBoardingDescriptionComponent = OnBoardingDescriptionComponent.createMessageComponent()
            )
        }

        private fun createVoteType(): OnBoardingBottomSheetComponent {
            return OnBoardingBottomSheetComponent(
                name = TYPE,
                onBoardingImageComponent = OnBoardingImageComponent.createVoteComponent(),
                onBoardingDescriptionComponent = OnBoardingDescriptionComponent.createVoteComponent()
            )
        }

    }

}

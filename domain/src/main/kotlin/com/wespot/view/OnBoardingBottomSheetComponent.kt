package com.wespot.view

data class OnBoardingBottomSheetComponent(
    val name: String,
    val onBoardingImageComponent: OnBoardingImageComponent,
    val onBoardingDescriptionComponent: com.wespot.view.OnBoardingDescriptionComponent
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
                onBoardingDescriptionComponent = com.wespot.view.OnBoardingDescriptionComponent.createMessageComponent()
            )
        }

        private fun createVoteType(): OnBoardingBottomSheetComponent {
            return OnBoardingBottomSheetComponent(
                name = TYPE,
                onBoardingImageComponent = OnBoardingImageComponent.createVoteComponent(),
                onBoardingDescriptionComponent = com.wespot.view.OnBoardingDescriptionComponent.createVoteComponent()
            )
        }

    }

}

package com.wespot.view

data class OnBoardingBottomSheetComponent(
    val name: String,
    val onBoardingWelcomePageComponent: OnBoardingWelcomePageComponent,
    val onBoardingExplanationComponent: OnBoardingExplanationComponent
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
                onBoardingWelcomePageComponent = OnBoardingWelcomePageComponent.createMessageComponent(),
                onBoardingExplanationComponent = OnBoardingExplanationComponent.createMessageComponent()
            )
        }

        private fun createVoteType(): OnBoardingBottomSheetComponent {
            return OnBoardingBottomSheetComponent(
                name = TYPE,
                onBoardingWelcomePageComponent = OnBoardingWelcomePageComponent.createVoteComponent(),
                onBoardingExplanationComponent = OnBoardingExplanationComponent.createVoteComponent()
            )
        }

    }

}

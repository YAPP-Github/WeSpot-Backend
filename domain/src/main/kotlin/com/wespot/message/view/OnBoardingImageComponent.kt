package com.wespot.message.view

import com.wespot.common.view.ButtonComponent
import com.wespot.common.view.ImageComponent
import com.wespot.common.view.TitleComponent

data class OnBoardingImageComponent(
    val titleComponent: TitleComponent,
    val imageComponent: ImageComponent,
    val buttonComponent: ButtonComponent
) {

    companion object {

        fun createBasicComponent(): OnBoardingImageComponent {
            return OnBoardingImageComponent(
                titleComponent = TitleComponent.from("마음에 대해 알려드릴께요"),
                imageComponent = ImageComponent.of("https://google.com", 100, 100),
                buttonComponent = ButtonComponent.from("확인해볼래요")
            )
        }

    }

}

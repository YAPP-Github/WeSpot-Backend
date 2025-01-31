package com.wespot.common.service.view

import com.wespot.common.dto.OnBoardingComponentRequest
import com.wespot.common.dto.view.OnBoardingResponse
import com.wespot.common.`in`.OnBoardingUseCase
import com.wespot.common.view.OnBoardingBottomSheetComponent
import org.springframework.stereotype.Service

@Service
class OnBoardingService : OnBoardingUseCase {

    override fun getOnBoardingComponents(category: OnBoardingComponentRequest): OnBoardingResponse {
        val onBoardingBottomSheetComponent = OnBoardingBottomSheetComponent.fromWithCategory(category.name)

        return OnBoardingResponse.from(onBoardingBottomSheetComponent)
    }

}

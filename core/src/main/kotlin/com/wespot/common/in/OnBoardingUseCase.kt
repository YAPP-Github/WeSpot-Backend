package com.wespot.common.`in`

import com.wespot.common.dto.OnBoardingComponentRequest
import com.wespot.common.dto.OnBoardingResponse

interface OnBoardingUseCase {

    fun getOnBoardingComponents(category: OnBoardingComponentRequest): List<OnBoardingResponse>

    fun viewOnBoardingSheetBy(category: OnBoardingComponentRequest)

}



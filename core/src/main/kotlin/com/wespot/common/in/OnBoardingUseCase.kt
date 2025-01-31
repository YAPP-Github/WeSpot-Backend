package com.wespot.common.`in`

import com.wespot.common.dto.OnBoardingComponentRequest
import com.wespot.common.dto.view.OnBoardingResponse

interface OnBoardingUseCase {

    fun getOnBoardingComponents(category: OnBoardingComponentRequest): OnBoardingResponse

}



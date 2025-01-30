package com.wespot.message.port.`in`

import com.wespot.message.dto.response.view.MessageOnBoardingResponse

interface MessageOnBoardingUseCase {

    fun getOnBoardingComponents(): MessageOnBoardingResponse

}



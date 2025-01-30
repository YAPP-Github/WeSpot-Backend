package com.wespot.message.service.view

import com.wespot.message.dto.response.view.MessageOnBoardingResponse
import com.wespot.message.port.`in`.MessageOnBoardingUseCase
import com.wespot.message.view.OnBoardingBottomSheetComponent
import org.springframework.stereotype.Service

@Service
class MessageOnBoardingService : MessageOnBoardingUseCase {

    override fun getOnBoardingComponents(): MessageOnBoardingResponse {
        val onBoardingBottomSheetComponent = OnBoardingBottomSheetComponent.create()

        return MessageOnBoardingResponse.from(onBoardingBottomSheetComponent)
    }

}

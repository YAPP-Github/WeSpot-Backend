package com.wespot.message.swagger

import com.wespot.message.dto.response.view.MessageOnBoardingResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity

@Tag(name = "온보딩 시트 서버드리븐 API", description = "마음 온보딩 시트를 받는 서버드리븐 API 입니다.")
interface MessageOnBoardingSwagger {

    @Operation(summary = "마음 온보딩 시트를 받는 서버드리븐 API 입니다.")
    fun getOnBoardingComponents(): ResponseEntity<MessageOnBoardingResponse>

}

package com.wespot.message.swagger

import com.wespot.common.dto.OnBoardingComponentRequest
import com.wespot.common.dto.view.OnBoardingResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity

@Tag(name = "온보딩 시트 서버드리븐 API", description = "온보딩 시트를 받는 서버드리븐 API 입니다.")
interface OnBoardingSwagger {

    @Operation(summary = "온보딩 시트를 받는 서버드리븐 API 입니다.")
    fun getOnBoardingComponents(category: OnBoardingComponentRequest): ResponseEntity<OnBoardingResponse>

    @Operation(summary = "유저가 온보딩 시트를 이미 받아본적이 있는지 여부를 확인하는 API 입니다.")
    fun isViewedOnBoardingSheetBy(category: OnBoardingComponentRequest): ResponseEntity<Boolean>

}

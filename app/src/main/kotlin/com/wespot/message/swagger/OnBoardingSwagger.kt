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

    @Operation(summary = "유저가 쪽지 혹은 투표 온보딩 시트를 조회함을 저장하는 API 입니다.")
    fun viewedOnBoardingSheetBy(category: OnBoardingComponentRequest): ResponseEntity<Unit>

}

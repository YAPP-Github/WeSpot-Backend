package com.wespot.common.onboarding

import com.wespot.common.dto.OnBoardingComponentRequest
import com.wespot.common.dto.view.OnBoardingResponse
import com.wespot.common.`in`.OnBoardingUseCase
import com.wespot.message.swagger.OnBoardingSwagger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/on-boarding")
class OnBoardingController(
    private val onBoardingUseCase: OnBoardingUseCase
) : OnBoardingSwagger {

    @GetMapping
    override fun getOnBoardingComponents(category: OnBoardingComponentRequest): ResponseEntity<OnBoardingResponse> {
        val response = onBoardingUseCase.getOnBoardingComponents(category)

        return ResponseEntity.ok(response)
    }

}

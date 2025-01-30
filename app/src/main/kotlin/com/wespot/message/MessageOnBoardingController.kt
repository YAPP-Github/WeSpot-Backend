package com.wespot.message

import com.wespot.message.dto.response.view.MessageOnBoardingResponse
import com.wespot.message.port.`in`.MessageOnBoardingUseCase
import com.wespot.message.swagger.MessageOnBoardingSwagger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/messages")
class MessageOnBoardingController(
    private val messageOnBoardingUseCase: MessageOnBoardingUseCase
) : MessageOnBoardingSwagger {

    @GetMapping("/on-boarding")
    override fun getOnBoardingComponents(): ResponseEntity<MessageOnBoardingResponse> {
        val response = messageOnBoardingUseCase.getOnBoardingComponents()

        return ResponseEntity.ok(response)
    }

}

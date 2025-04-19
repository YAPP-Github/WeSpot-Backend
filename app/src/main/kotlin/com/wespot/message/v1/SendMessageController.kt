package com.wespot.message.v1

import com.wespot.message.dto.request.SendMessageRequest
import com.wespot.message.dto.response.SendMessageResponse
import com.wespot.message.port.`in`.SendMessageUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/messages")
class SendMessageController(
    private val sendMessageUseCase: SendMessageUseCase
) {

    @PostMapping("/send")
    fun sendMessage(
        @RequestBody sendMessageRequest: SendMessageRequest
    ): ResponseEntity<SendMessageResponse> {
        val response = sendMessageUseCase.send(sendMessageRequest)

        return ResponseEntity.ok()
            .body(response)
    }

}

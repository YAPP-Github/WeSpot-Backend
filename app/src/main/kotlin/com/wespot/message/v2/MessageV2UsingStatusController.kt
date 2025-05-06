package com.wespot.message.v2

import com.wespot.message.dto.response.MessageV2StatusResponse
import com.wespot.message.port.`in`.MessageV2UsingStatusUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v2/messages")
class MessageV2UsingStatusController(
    private val messageV2UsingStatusUseCase: MessageV2UsingStatusUseCase,
) {

    @GetMapping("/status")
    fun getMessageStatus(): ResponseEntity<MessageV2StatusResponse> {
        val response = messageV2UsingStatusUseCase.getMessageStatus()

        return ResponseEntity.ok(response)
    }

}

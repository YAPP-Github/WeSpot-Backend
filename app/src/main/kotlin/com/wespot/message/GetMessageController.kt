package com.wespot.message

import com.wespot.message.dto.response.MessageListResponse
import com.wespot.message.dto.response.MessageResponse
import com.wespot.message.dto.response.SendMessageStatusResponse
import com.wespot.message.port.`in`.GetMessageUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/messages")
class GetMessageController(
    private val getMessageUseCase: GetMessageUseCase
) {

    @GetMapping("/status/me")
    fun getSendStatus(): ResponseEntity<SendMessageStatusResponse> {
        val status = getMessageUseCase.status()

        return ResponseEntity.ok()
            .body(status)
    }

    @GetMapping("")
    fun getMessages(
        @RequestParam(required = false, defaultValue = "0") cursorId: Long,
        @RequestParam type: MessageType
    ): ResponseEntity<Any> {
        val response: MessageListResponse = when (type) {
            MessageType.SENT -> getMessageUseCase.getSendMessages(cursorId)
            MessageType.RECEIVED -> getMessageUseCase.getReceivedMessages(cursorId)
        }

        return ResponseEntity.ok()
            .body(response)
    }

    @GetMapping("/{messageId}")
    fun getMessage(
        @PathVariable messageId: Long
    ): ResponseEntity<MessageResponse> {
        val response = getMessageUseCase.getMessage(messageId)

        return ResponseEntity.ok()
            .body(response)
    }

}

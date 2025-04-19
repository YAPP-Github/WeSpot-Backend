package com.wespot.message.v1

import com.wespot.message.dto.request.UpdateMessageRequest
import com.wespot.message.port.`in`.ModifyMessageUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/messages")
class ModifyMessageController(
    private val modifyMessageUseCase: ModifyMessageUseCase
) {

    @PutMapping("/{messageId}")
    fun modifyMessage(
        @PathVariable messageId: Long,
        @RequestBody updateMessageRequest: UpdateMessageRequest
    ): ResponseEntity<Unit> {
        modifyMessageUseCase.updateMessage(
            messageId = messageId,
            updateMessageRequest = updateMessageRequest
        )

        return ResponseEntity.noContent()
            .build()
    }

    @PutMapping("/{messageId}/read")
    fun readMessage(
        @PathVariable messageId: Long
    ): ResponseEntity<Unit> {
        modifyMessageUseCase.readMessage(messageId)

        return ResponseEntity.noContent()
            .build()
    }

}

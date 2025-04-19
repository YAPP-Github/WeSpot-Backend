package com.wespot.message.v1

import com.wespot.message.port.`in`.DeleteMessageUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/messages")
class DeleteMessageController(
    private val deleteMessageUseCase: DeleteMessageUseCase
) {

    @DeleteMapping("/{messageId}")
    fun deleteMessage(
        @PathVariable messageId: Long
    ): ResponseEntity<Unit> {
        deleteMessageUseCase.deleteMessage(messageId)

        return ResponseEntity.noContent()
            .build()
    }

}

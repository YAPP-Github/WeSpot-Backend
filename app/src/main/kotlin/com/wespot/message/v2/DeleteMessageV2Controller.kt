package com.wespot.message.v2

import com.wespot.message.port.`in`.DeleteMessageV2UseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v2/messages")
class DeleteMessageV2Controller(
    private val deleteMessageV2UseCase: DeleteMessageV2UseCase
) {

    @DeleteMapping("/{messageId}")
    fun deleteMessage(@PathVariable messageId: Long): ResponseEntity<Unit> {
        deleteMessageV2UseCase.deleteMessage(messageId = messageId)

        return ResponseEntity.noContent()
            .build()
    }
}

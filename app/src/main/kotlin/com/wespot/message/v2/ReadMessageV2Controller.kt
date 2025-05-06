package com.wespot.message.v2

import com.wespot.message.port.`in`.ReadMessageV2UseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v2/messages")
class ReadMessageV2Controller(
    private val readMessageV2UseCase: ReadMessageV2UseCase,
) {

    @PatchMapping("/{messageId}/read")
    fun readMessage(@PathVariable messageId: Long): ResponseEntity<Unit> {
        readMessageV2UseCase.readMessage(messageId)

        return ResponseEntity.noContent()
            .build()
    }

}

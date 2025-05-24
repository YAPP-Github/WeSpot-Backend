package com.wespot.message.v2

import com.wespot.message.dto.request.CreatedMessageV2Request
import com.wespot.message.port.`in`.CreatedMessageV2UseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v2/messages")
class CreateMessageV2Controller(
    private val createdMessageV2UseCase: CreatedMessageV2UseCase,
) {

    @PostMapping
    fun createMessageV2(
        @RequestBody createdMessageV2Request: CreatedMessageV2Request
    ): ResponseEntity<Unit> {
        createdMessageV2UseCase.createMessage(createdMessageV2Request)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }

}

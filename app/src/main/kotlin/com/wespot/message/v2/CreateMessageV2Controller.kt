package com.wespot.message.v2

import com.wespot.message.dto.request.CreatedMessageV2Request
import com.wespot.message.port.`in`.CreatedMessageV2UseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v2/messages")
class CreateMessageV2Controller(
    private val createdMessageV2Usecase: CreatedMessageV2UseCase
) {

    @PostMapping
    fun createMessageV2(
        createdMessageV2Request: CreatedMessageV2Request
    ): ResponseEntity<Unit> {
        createdMessageV2Usecase.createMessage(createdMessageV2Request)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build();
    }

}

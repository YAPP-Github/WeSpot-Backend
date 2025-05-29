package com.wespot.message.v2

import com.wespot.message.dto.request.AnswerMessageRequest
import com.wespot.message.port.`in`.AnswerMessageV2UseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v2/messages")
class AnswerMessageV2Controller(
    private val answerMessageV2UseCase: AnswerMessageV2UseCase
) {

    @PostMapping("/{messageId}/answer")
    fun answerMessage(
        @PathVariable messageId: Long,
        @RequestBody answerMessageRequest: AnswerMessageRequest
    ): ResponseEntity<Unit> {
        answerMessageV2UseCase.answerMessage(messageRoomId = messageId, answerMessageRequest = answerMessageRequest)

        return ResponseEntity.status(HttpStatus.CREATED)
            .build()
    }


}

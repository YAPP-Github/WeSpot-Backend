package com.wespot.message.v2

import com.wespot.message.port.`in`.UpdatedMessageV2UseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v2/messages")
class UpdatedMessageV2Controller(
    val updatedMessageV2UseCase: UpdatedMessageV2UseCase,
) {

    @PatchMapping("/{messageId}/bookmark")
    fun bookmarkMessage(@PathVariable messageId: Long): ResponseEntity<Unit> {
        updatedMessageV2UseCase.bookmarkMessage(messageId)

        return ResponseEntity.noContent()
            .build()
    }

}

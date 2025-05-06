package com.wespot.message.v2

import com.wespot.message.port.`in`.BlockedMessageV2UseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v2/messages")
class BlockedMessageV2Controller(
    private val blockedMessageV2UseCase: BlockedMessageV2UseCase,
) {

    @PatchMapping("/{messageId}/block")
    fun blockMessage(
        @PathVariable messageId: Long
    ): ResponseEntity<Unit> {
        blockedMessageV2UseCase.blockMessage(messageId)

        return ResponseEntity.noContent() // 읽음 처리에 알림 추가
            .build()
    }

}

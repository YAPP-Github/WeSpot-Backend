package com.wespot.user

import com.wespot.user.port.`in`.UsedAnswerMessageUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v2/messages/answer/first")
class UsedAnswerMessageController(
    private val usedAnswerMessageUseCase: UsedAnswerMessageUseCase,
) {

    @GetMapping
    fun isUsedAnswerMessageFeature(): ResponseEntity<Boolean> {
        val response = usedAnswerMessageUseCase.isUsedAnswerMessageFeature()

        return ResponseEntity.ok(response)
    }

}

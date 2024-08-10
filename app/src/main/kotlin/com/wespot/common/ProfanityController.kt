package com.wespot.common

import com.wespot.common.dto.CheckProfanityRequest
import com.wespot.common.`in`.CheckProfanityUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/check-profanity")
class ProfanityController(
    private val checkProfanityUseCase: CheckProfanityUseCase
) {

    @PostMapping
    fun checkProfanity(@RequestBody message: CheckProfanityRequest): ResponseEntity<Unit> {
        checkProfanityUseCase.checkProfanity(message)

        return ResponseEntity.noContent()
            .build()
    }

}

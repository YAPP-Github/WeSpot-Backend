package com.wespot.message.v2

import com.wespot.message.dto.response.AnonymousProfileResponse
import com.wespot.message.port.`in`.GetAnonymousProfileUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/messages")
class GetAnonymousProfileController(
    private val getAnonymousProfileUseCase: GetAnonymousProfileUseCase,
) {

    @GetMapping("/receiver/{receiverId}/profiles")
    fun getAnonymousProfileByReceiverId(
        @PathVariable receiverId: Long
    ): ResponseEntity<List<AnonymousProfileResponse>> {
        val response = getAnonymousProfileUseCase.getAnonymousProfileByReceiverId(receiverId)

        return ResponseEntity.ok()
            .body(response)
    }
}

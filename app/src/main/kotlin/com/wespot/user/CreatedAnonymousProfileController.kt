package com.wespot.user

import com.wespot.user.dto.request.CreatedAnonymousProfileRequest
import com.wespot.user.dto.request.UpdatedAnonymousProfileRequest
import com.wespot.user.port.`in`.AnonymousProfileUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/messages")
class CreatedAnonymousProfileController(
    private val anonymousProfileUseCase: AnonymousProfileUseCase
) {

    @PostMapping("/profiles")
    fun createAnonymousProfile(@RequestBody createdAnonymousProfileRequest: CreatedAnonymousProfileRequest): ResponseEntity<Unit> {
        anonymousProfileUseCase.createAnonymousProfile(createdAnonymousProfileRequest)

        return ResponseEntity.status(HttpStatus.CREATED)
            .build()
    }

    @PutMapping("/profiles/{id}")
    fun unblockUser(
        @PathVariable id: Long,
        @RequestBody updatedAnonymousProfileRequest: UpdatedAnonymousProfileRequest
    ): ResponseEntity<Unit> {
        anonymousProfileUseCase.updateAnonymousProfile(id, updatedAnonymousProfileRequest)

        return ResponseEntity.noContent()
            .build()
    }

}

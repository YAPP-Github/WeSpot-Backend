package com.wespot.user

import com.wespot.user.dto.request.UpdateProfileRequest
import com.wespot.user.dto.response.BackgroundListResponse
import com.wespot.user.dto.response.CharacterListResponse
import com.wespot.user.dto.response.UserResponse
import com.wespot.user.port.`in`.UserUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userUseCase: UserUseCase
) {

    @GetMapping("/me")
    fun me(): ResponseEntity<UserResponse> {
        val response = userUseCase.me()

        return ResponseEntity.ok()
            .body(response)
    }

    @PutMapping("/me")
    fun updateProfile(
        @RequestBody profile: UpdateProfileRequest
    ): ResponseEntity<Unit> {
        userUseCase.updateProfile(profile)

        return ResponseEntity.noContent()
            .build()
    }

    @GetMapping("/backgrounds")
    fun backgrounds(): ResponseEntity<BackgroundListResponse> {
        val response = userUseCase.backgrounds()

        return ResponseEntity.ok()
            .body(response)
    }

    @GetMapping("/characters")
    fun characters(): ResponseEntity<CharacterListResponse> {
        val response = userUseCase.characters()

        return ResponseEntity.ok()
            .body(response)
    }

}

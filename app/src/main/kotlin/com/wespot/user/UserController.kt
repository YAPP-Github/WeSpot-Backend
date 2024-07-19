package com.wespot.user

import com.wespot.user.dto.request.UpdateProfileRequest
import com.wespot.user.dto.response.BackgroundListResponse
import com.wespot.user.dto.response.CharacterListResponse
import com.wespot.user.dto.response.UserResponse
import com.wespot.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/me")
    fun me(): ResponseEntity<UserResponse> {
        val response = userService.me()

        return ResponseEntity.ok()
            .body(response)
    }

    @PutMapping("/me")
    fun updateProfile(
        @RequestBody profile: UpdateProfileRequest
    ): ResponseEntity<Unit> {
        userService.updateProfile(profile)

        return ResponseEntity.noContent()
            .build()
    }

    @GetMapping("/backgrounds")
    fun backgrounds(): ResponseEntity<BackgroundListResponse> {
        val response = userService.backgrounds()

        return ResponseEntity.ok()
            .body(response)
    }

    @GetMapping("/characters")
    fun characters(): ResponseEntity<CharacterListResponse> {
        val response = userService.characters()

        return ResponseEntity.ok()
            .body(response)
    }

}

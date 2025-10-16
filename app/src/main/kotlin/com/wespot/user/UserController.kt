package com.wespot.user

import com.wespot.user.dto.request.ModifiedSettingRequest
import com.wespot.user.dto.request.UpdateProfileRequest
import com.wespot.user.dto.response.BackgroundListResponse
import com.wespot.user.dto.response.CharacterListResponse
import com.wespot.user.dto.response.CheckedRestrictionResponse
import com.wespot.user.dto.response.UserResponse
import com.wespot.user.dto.response.UserSettingResponse
import com.wespot.user.port.`in`.CheckedUserRestrictionUseCase
import com.wespot.user.port.`in`.UserSettingUseCase
import com.wespot.user.port.`in`.UserUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userUseCase: UserUseCase,
    private val userSettingUseCase: UserSettingUseCase,
    private val checkedUserRestrictionUseCase: CheckedUserRestrictionUseCase
) {

    @GetMapping("/me")
    fun me(): ResponseEntity<UserResponse> {
        val response = userUseCase.me()

        return ResponseEntity.ok()
            .body(response)
    }

    @PostMapping("/allow/policy")
    fun allowNewPolicy(policyType: PolicyType): ResponseEntity<Unit> {
        userUseCase.allowNewPolicy(policyType)

        return ResponseEntity.status(HttpStatus.CREATED)
            .build()
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

    @GetMapping("/settings")
    fun getUserSettings(): ResponseEntity<UserSettingResponse> {
        val response = userSettingUseCase.getSetting()

        return ResponseEntity.ok(response)
    }

    @PutMapping("/settings")
    fun modifyUserSetting(
        @RequestBody request: ModifiedSettingRequest
    ): ResponseEntity<Unit> {
        userSettingUseCase.modifySetting(request)

        return ResponseEntity.noContent()
            .build()
    }

    @GetMapping("/restrictions/me")
    fun checkMyRestriction(): ResponseEntity<CheckedRestrictionResponse> {
        val response = checkedUserRestrictionUseCase.getUserRestriction()

        return ResponseEntity.ok(response)
    }

}

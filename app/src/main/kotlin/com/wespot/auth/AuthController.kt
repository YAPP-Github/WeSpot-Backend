package com.wespot.auth

import com.wespot.auth.dto.request.AuthLoginRequest
import com.wespot.auth.dto.request.RefreshTokenRequest
import com.wespot.auth.dto.request.SignUpRequest
import com.wespot.auth.dto.response.SignUpResponse
import com.wespot.auth.dto.response.TokenResponse
import com.wespot.auth.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun signIn(
        @RequestBody request: AuthLoginRequest
    ): Any {

        val response = authService.socialAccess(request)

        return if (response is SignUpResponse) {
            ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
        } else {
            ResponseEntity.ok()
                .body(response)
        }

    }

    @PostMapping("/signup")
    fun signUp(
        @RequestBody request: SignUpRequest
    ): ResponseEntity<TokenResponse> {

        val signUp = authService.signUp(request)

        return ResponseEntity.ok()
            .body(signUp)

    }

    @PostMapping("/reissue")
    fun reissue(
        @RequestBody request: RefreshTokenRequest
    ): ResponseEntity<TokenResponse> {

        val response = authService.reIssueToken(request)

        return ResponseEntity.ok()
            .body(response)

    }

    @PostMapping("/revoke")
    fun revoke(): ResponseEntity<Unit> {

        authService.revoke()

        return ResponseEntity.noContent().build()

    }

}

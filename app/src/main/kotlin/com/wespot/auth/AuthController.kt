package com.wespot.auth

import com.wespot.auth.dto.request.AdminLoginRequest
import com.wespot.auth.dto.request.AuthLoginRequest
import com.wespot.auth.dto.request.RefreshTokenRequest
import com.wespot.auth.dto.request.SignUpRequest
import com.wespot.auth.dto.response.SignUpResponse
import com.wespot.auth.dto.response.TokenAndUserDetailResponse
import com.wespot.auth.dto.response.TokenResponse
import com.wespot.auth.port.`in`.AuthUseCase
import com.wespot.auth.port.`in`.LogoutUsecase
import com.wespot.auth.swagger.AuthSwagger
import com.wespot.config.ratelimit.UserRateLimit
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authUseCase: AuthUseCase,
    private val logoutUsecase: LogoutUsecase
) : AuthSwagger {

    @PostMapping("/login")
    fun signIn(
        @RequestBody request: AuthLoginRequest
    ): Any {
        val response = authUseCase.socialAccess(request)

        return if (response is SignUpResponse) {
            ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(response)
        } else {
            ResponseEntity.ok()
                .body(response)
        }
    }

    @PostMapping("/signup")
    @UserRateLimit
    fun signUp(
        @RequestBody request: SignUpRequest
    ): ResponseEntity<TokenAndUserDetailResponse> {
        val signUp = authUseCase.signUp(request)

        return ResponseEntity.ok()
            .body(signUp)
    }

    @PostMapping("/reissue")
    fun reissue(
        @RequestBody request: RefreshTokenRequest
    ): ResponseEntity<TokenResponse> {
        val response = authUseCase.reIssueToken(request)

        return ResponseEntity.ok()
            .body(response)
    }

    @PostMapping("/revoke")
    fun revoke(): ResponseEntity<Unit> {
        authUseCase.revoke()

        return ResponseEntity.noContent().build()
    }

    @PostMapping("/admin/login")
    fun adminLogin(
        @RequestBody request: AdminLoginRequest
    ): ResponseEntity<TokenResponse> {
        val response = authUseCase.adminLogin(request)

        return ResponseEntity.ok()
            .body(response)
    }

    @PatchMapping("/logout")
    override fun logout(): ResponseEntity<Unit> {
        logoutUsecase.logout()

        return ResponseEntity.noContent()
            .build()
    }

}

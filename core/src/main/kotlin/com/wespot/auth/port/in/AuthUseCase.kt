package com.wespot.auth.port.`in`

import com.wespot.auth.dto.request.AuthLoginRequest
import com.wespot.auth.dto.request.RefreshTokenRequest
import com.wespot.auth.dto.request.SignUpRequest
import com.wespot.auth.dto.response.TokenResponse

interface AuthUseCase {

    fun socialAccess(authLoginRequest: AuthLoginRequest): Any

    fun signUp(signUpRequest: SignUpRequest): TokenResponse

    fun reIssueToken(refreshTokenRequest: RefreshTokenRequest): TokenResponse

    fun revoke()

}

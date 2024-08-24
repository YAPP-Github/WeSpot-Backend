package com.wespot.auth.dto.request

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

data class AdminLoginRequest(
    val email: String,
    val password: String,
){

    fun toAuthentication(): UsernamePasswordAuthenticationToken {
        return UsernamePasswordAuthenticationToken(email, password)
    }
}

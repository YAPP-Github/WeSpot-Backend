package com.wespot.auth.dto

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

data class SignInRequest(
    val email: String,
    val password: String
){
    fun toAuthentication(): UsernamePasswordAuthenticationToken {
        return UsernamePasswordAuthenticationToken(email, password)
    }
}

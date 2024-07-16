package com.wespot.auth.dto

data class AuthData(
    val email: String,
    val socialRefreshToken: String,
    val socialEmail: String,
)
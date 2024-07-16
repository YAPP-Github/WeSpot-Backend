package com.wespot.auth.dto

data class SignInResponse (
    val accessToken: String,
    val refreshToken: String
)
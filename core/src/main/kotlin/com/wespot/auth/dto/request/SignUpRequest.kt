package com.wespot.auth.dto.request

data class SignUpRequest(
    val name: String,
    val gender: String,
    val schoolId: Long,
    val grade: Int,
    val classNumber: Int,
    val consents: ConsentsRequest,
    val signUpToken: String,
)

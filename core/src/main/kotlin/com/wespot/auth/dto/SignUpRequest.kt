package com.wespot.auth.dto

data class SignUpRequest(
    val singUpToken: String,
    val profile: ProfileRequest,
    val userConsent: UserConsentRequest,
    val school: SchoolRequest,
    val grade: Int,
    val groupNumber: Int,
)
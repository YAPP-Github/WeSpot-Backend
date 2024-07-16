package com.wespot.auth.dto.request

data class SignUpRequest(
    val signUpToken: String,
    val profile: ProfileRequest,
    val userConsent: UserConsentRequest,
    val schoolId: Long,
    val grade: Int,
    val groupNumber: Int,
)
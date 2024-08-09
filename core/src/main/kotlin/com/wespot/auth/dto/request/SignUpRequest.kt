package com.wespot.auth.dto.request

import com.wespot.user.Gender

data class SignUpRequest(
    val name: String,
    val gender: Gender,
    val schoolId: Long,
    val grade: Int,
    val classNumber: Int,
    val consents: ConsentsRequest,
    val signUpToken: String,
)

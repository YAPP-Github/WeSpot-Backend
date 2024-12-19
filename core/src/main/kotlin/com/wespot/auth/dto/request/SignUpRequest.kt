package com.wespot.auth.dto.request

import com.wespot.user.Gender

data class SignUpRequest(
    val name: String,
    val gender: Gender,
    val schoolId: Long,
    val grade: Int,
    val classNumber: Int,
    val introduction: String?,
    val consents: ConsentsRequest,
    val signUpToken: String,
    val profileUrl: String?,
    val androidVersionNameWhenSignUp: String?,
    val iosVersionNameWhenSignUp: String?
)

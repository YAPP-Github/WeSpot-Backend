package com.wespot.user.dto.request

import com.wespot.auth.dto.request.ProfileRequest

data class UpdateProfileRequest(
    val introduction: String?,
    val profile: ProfileRequest?
)

package com.wespot.auth.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class AuthData @JsonCreator constructor(
    @JsonProperty("email") val email: String,
    @JsonProperty("socialRefreshToken") val socialRefreshToken: String,
    @JsonProperty("socialEmail") val socialEmail: String,
    @JsonProperty("fcmToken") val fcmToken: String?,
)

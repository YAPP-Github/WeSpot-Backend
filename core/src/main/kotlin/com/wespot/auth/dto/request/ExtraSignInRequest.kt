package com.wespot.auth.dto.request

class ExtraSignInRequest(
    val fcmToken: String?,
    val androidVersionName: String?,
    val iosVersionName: String?
) {
}

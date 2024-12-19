package com.wespot.notification

enum class LatestVersionType(
    val variableName: String
) {

    MIN_VERSION("0.0.0"),
    ANDROID("ANDROID_LATEST_VERSION"),
    IOS("IOS_LATEST_VERSION"),
    ;

}

package com.wespot.user

import com.wespot.notification.LatestVersionType
import java.time.LocalDateTime

data class UserVersion(

    val id: Long,

    val userId: Long,

    val iosVersionName: String,

    val androidVersionName: String,

    val iosVersionNameWhenSignUp: String,

    val androidVersionNameWhenSignUp: String,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime

) {

    companion object {

        fun createInitialState(userId: Long): UserVersion {
            return UserVersion(
                id = 0,
                userId = userId,
                iosVersionName = LatestVersionType.MIN_VERSION.variableName,
                androidVersionName = LatestVersionType.MIN_VERSION.variableName,
                iosVersionNameWhenSignUp = LatestVersionType.MIN_VERSION.variableName,
                androidVersionNameWhenSignUp = LatestVersionType.MIN_VERSION.variableName,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        }

        fun createWithSignUp(
            userId: Long,
            androidVersionNameWhenSignUp: String?,
            iosVersionNameWhenSignUp: String?
        ): UserVersion {
            return UserVersion(
                id = 0,
                userId = userId,
                iosVersionName = LatestVersionType.MIN_VERSION.variableName,
                androidVersionName = LatestVersionType.MIN_VERSION.variableName,
                iosVersionNameWhenSignUp = iosVersionNameWhenSignUp ?: LatestVersionType.MIN_VERSION.variableName,
                androidVersionNameWhenSignUp = androidVersionNameWhenSignUp
                    ?: LatestVersionType.MIN_VERSION.variableName,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        }

    }

    fun updateWithLogin(androidVersionName: String?, iosVersionName: String?): UserVersion {
        return this.copy(
            id = this.id,
            userId = this.userId,
            iosVersionName = iosVersionName ?: this.iosVersionName,
            androidVersionName = androidVersionName ?: this.androidVersionName,
            iosVersionNameWhenSignUp = this.iosVersionNameWhenSignUp,
            androidVersionNameWhenSignUp = this.androidVersionNameWhenSignUp,
            createdAt = this.createdAt,
            updatedAt = LocalDateTime.now()
        )
    }

    fun isPossibleToSendUpdateNotification(androidLatestVersion: String, iosLatestVersion: String): Boolean {
        if (isSignUpWhenLatestVersion()) {
            return false
        }

        return androidVersionName == androidLatestVersion || iosVersionName == iosLatestVersion
    }

    private fun isSignUpWhenLatestVersion(): Boolean {
        val equalVersions = mutableListOf<String>()

        if (androidVersionName == androidVersionNameWhenSignUp) {
            equalVersions.add(androidVersionName)
        }
        if (iosVersionName == iosVersionNameWhenSignUp) {
            equalVersions.add(iosVersionName)
        }

//        val isNotSignUpWhenLatestVersion = equalVersions.isNotEmpty()
//            && equalVersions.all { it == LatestVersionType.MIN_VERSION.variableName }

        return !equalVersions.all { it == LatestVersionType.MIN_VERSION.variableName }
    }

}

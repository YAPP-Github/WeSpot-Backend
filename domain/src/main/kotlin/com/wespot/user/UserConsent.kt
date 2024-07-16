package com.wespot.user

import java.time.LocalDateTime

data class UserConsent(
    val id: Long,
    val user: User,
    val consentType: ConsentType?,
    val consentValue: Boolean?,
    val consentedAt: LocalDateTime?
) {
    companion object {
        fun create(
            user: User,
            consentType: ConsentType,
            consentValue: Boolean,
            consentedAt: LocalDateTime
        ) =
            UserConsent(
                id = 0,
                user = user,
                consentType = consentType,
                consentValue = consentValue,
                consentedAt = consentedAt
            )
    }
}

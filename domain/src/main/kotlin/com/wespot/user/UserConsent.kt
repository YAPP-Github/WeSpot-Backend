package com.wespot.user

import java.time.LocalDateTime

data class UserConsent(
    val id: Long,
    val consentType: ConsentType?,
    val consentValue: Boolean?,
    val consentedAt: LocalDateTime?
) {
    companion object {
        fun create(
            consentType: ConsentType,
            consentValue: Boolean,
            consentedAt: LocalDateTime
        ) =
            UserConsent(
                id = 0,
                consentType = consentType,
                consentValue = consentValue,
                consentedAt = consentedAt
            )
    }
}

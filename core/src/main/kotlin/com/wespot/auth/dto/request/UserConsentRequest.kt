package com.wespot.auth.dto.request

import com.wespot.user.ConsentType

data class UserConsentRequest(
    val consentType: ConsentType,
    val consentValue: Boolean
)

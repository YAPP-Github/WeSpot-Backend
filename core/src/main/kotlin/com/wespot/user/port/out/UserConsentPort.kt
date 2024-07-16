package com.wespot.user.port.out

import com.wespot.user.UserConsent

interface UserConsentPort {

    fun save(userConsent: UserConsent): UserConsent
}
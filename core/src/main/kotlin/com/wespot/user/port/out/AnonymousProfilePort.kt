package com.wespot.user.port.out

import com.wespot.user.message.AnonymousProfile

interface AnonymousProfilePort {

    fun findByProfileId(profileId: Long): AnonymousProfile?

    fun save(anonymousProfile: AnonymousProfile): AnonymousProfile

}

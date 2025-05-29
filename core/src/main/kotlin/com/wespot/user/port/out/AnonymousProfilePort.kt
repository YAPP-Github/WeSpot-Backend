package com.wespot.user.port.out

import com.google.api.services.storage.model.Bucket.Owner
import com.wespot.user.message.AnonymousProfile

interface AnonymousProfilePort {

    fun findByProfileId(profileId: Long): AnonymousProfile?

    fun save(anonymousProfile: AnonymousProfile): AnonymousProfile

    fun findAllByOwnerIdAndReceiverId(ownerId: Long, receiverId: Long): List<AnonymousProfile>

}

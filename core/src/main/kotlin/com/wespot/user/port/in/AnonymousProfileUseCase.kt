package com.wespot.user.port.`in`

import com.wespot.user.dto.request.CreatedAnonymousProfileRequest
import com.wespot.user.dto.request.UpdatedAnonymousProfileRequest
import com.wespot.user.message.AnonymousProfile


interface AnonymousProfileUseCase {

    fun createAnonymousProfile(
        createdAnonymousProfileRequest: CreatedAnonymousProfileRequest
    ): AnonymousProfile

    fun updateAnonymousProfile(
        profileId: Long,
        updatedAnonymousProfileRequest: UpdatedAnonymousProfileRequest
    ): AnonymousProfile

}

package com.wespot.user.port.`in`

import com.wespot.user.dto.request.CreatedAnonymousProfileRequest
import com.wespot.user.dto.request.UpdatedAnonymousProfileRequest


interface AnonymousProfileUseCase {

    fun createAnonymousProfile(createdAnonymousProfileRequest: CreatedAnonymousProfileRequest)

    fun updateAnonymousProfile(profileId: Long, updatedAnonymousProfileRequest: UpdatedAnonymousProfileRequest)

}

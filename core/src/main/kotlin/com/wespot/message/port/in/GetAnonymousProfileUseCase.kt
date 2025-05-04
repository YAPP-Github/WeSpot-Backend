package com.wespot.message.port.`in`

import com.wespot.message.dto.response.AnonymousProfileResponse

interface GetAnonymousProfileUseCase {

    fun getAnonymousProfileByReceiverId(receiverId: Long): List<AnonymousProfileResponse>

}

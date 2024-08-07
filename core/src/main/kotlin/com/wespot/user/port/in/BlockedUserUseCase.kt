package com.wespot.user.port.`in`

import com.wespot.user.dto.response.BlockedUserResponse

interface BlockedUserUseCase {

    fun blockedUser(messageId: Long): BlockedUserResponse

    fun unblockedUser(messageId: Long): Unit

}

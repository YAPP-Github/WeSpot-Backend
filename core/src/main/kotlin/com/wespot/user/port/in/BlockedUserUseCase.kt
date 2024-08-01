package com.wespot.user.port.`in`

interface BlockedUserUseCase {

    fun blockedUser(messageId: Long): Boolean

}

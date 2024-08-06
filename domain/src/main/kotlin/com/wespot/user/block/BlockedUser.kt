package com.wespot.user.block

import java.time.LocalDateTime

data class BlockedUser(
    val id: Long,
    val blockerId: Long,
    val blockedId: Long,
    val messageId: Long,
    val createdAt: LocalDateTime
){

    companion object {
        fun create(
            blockerId: Long,
            blockedId: Long,
            messageId: Long
        ) =
            BlockedUser(
                id = 0,
                blockerId = blockerId,
                blockedId = blockedId,
                messageId = messageId,
                createdAt = LocalDateTime.now()
            )
    }

}

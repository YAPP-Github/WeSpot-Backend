package com.wespot.user.fixture

import com.wespot.user.block.BlockedUser
import java.time.LocalDateTime

object BlockedUserFixture {

    fun createWithIdAndBlockedIdAndBlockerId(
        id: Long,
        blockedId: Long,
        blockerId: Long
    ) = BlockedUser(
        id = id,
        blockedId = blockedId,
        blockerId = blockerId,
        createdAt = LocalDateTime.now()
    )
}

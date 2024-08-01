package com.wespot.user.port.out

import com.wespot.user.block.BlockedUser
import java.time.LocalDateTime

interface BlockedUserPort {

    fun existsByBlockerIdAndBlockedId(blockerId: Long, blockedId: Long): Boolean

    fun deleteByBlockerIdAndBlockedId(blockerId: Long, blockedId: Long)

    fun save(blockedUser: BlockedUser): BlockedUser

    fun findAllByBlockerId(blockerId: Long): List<BlockedUser>

    fun findBlockedTime(blockerId: Long, blockedId: Long): LocalDateTime?

}

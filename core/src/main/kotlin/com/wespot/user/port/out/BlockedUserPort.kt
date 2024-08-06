package com.wespot.user.port.out

import com.wespot.user.block.BlockedUser
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime

interface BlockedUserPort {

    fun existsByBlockerIdAndBlockedId(
        blockerId: Long,
        blockedId: Long
    ): Boolean

    fun existsByBlockerIdAndBlockedIdAndMessageId(
        blockerId: Long,
        blockedId: Long,
        messageId: Long
    ): Boolean

    fun deleteByBlockerIdAndBlockedIdAndMessageId(
        blockerId: Long,
        blockedId: Long,
        messageId: Long
    )

    fun save(blockedUser: BlockedUser): BlockedUser

    fun findAllByBlockerIdAfterCursor(
        blockerId: Long,
        cursorId: Long,
        pageable: Pageable
    ): List<BlockedUser>

    fun countBlockedUsersAfterCursor(
        blockerId: Long,
        cursorId: Long,
        pageable: Pageable
    ): Long

    fun findAllByBlockerId(blockerId: Long): List<BlockedUser>

}

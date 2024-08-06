package com.wespot.user.repository

import com.wespot.user.entity.BlockedUserJpaEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface BlockedUserJpaRepository: JpaRepository<BlockedUserJpaEntity, Long> {

    fun existsByBlockerIdAndBlockedId(blockerId: Long, blockedId: Long): Boolean

    fun existsByBlockerIdAndBlockedIdAndMessageId(blockerId: Long, blockedId: Long, messageId: Long): Boolean

    fun deleteByBlockerIdAndBlockedIdAndMessageId(blockerId: Long, blockedId: Long, messageId: Long)

    @Query(
        """
        SELECT bu
        FROM BlockedUserJpaEntity bu
        WHERE bu.blockerId = :blockerId
        AND bu.id < :cursorId
        ORDER BY bu.id DESC
    """
    )
    fun findAllByBlockerIdAfterCursor(
        @Param("blockerId") blockerId: Long,
        @Param("cursorId") cursorId: Long,
        pageable: Pageable
    ): List<BlockedUserJpaEntity>

    @Query(
        """
        SELECT COUNT(bu)
        FROM BlockedUserJpaEntity bu
        WHERE bu.blockerId = :blockerId
        AND bu.id < :cursorId
    """
    )
    fun countBlockedUsersAfterCursor(
        @Param("blockerId") blockerId: Long,
        @Param("cursorId") cursorId: Long,
    ): Long


    @Query(
        """
        SELECT bu
        FROM BlockedUserJpaEntity bu
        WHERE bu.blockerId = :blockerId
        ORDER BY bu.id DESC
        """
    )
    fun findAllByBlockerId(
        @Param("blockerId") blockerId: Long
    ): List<BlockedUserJpaEntity>
}

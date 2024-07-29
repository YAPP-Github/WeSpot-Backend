package com.wespot.message

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface MessageJpaRepository : JpaRepository<MessageJpaEntity, Long> {

    @Query("SELECT COUNT(m) FROM MessageJpaEntity m WHERE m.senderId = :senderId AND DATE(m.baseEntity.createdAt) = :date")
    fun countMessagesBySenderIdAndDate(
        @Param("senderId") senderId: Long,
        @Param("date") date: LocalDate
    ): Int

    @Query("SELECT COUNT(m) > 0 FROM MessageJpaEntity m WHERE m.senderId = :senderId AND m.receiverId = :receiverId AND DATE(m.baseEntity.createdAt) = :date")
    fun existsBySenderIdAndReceiverIdAndDate(
        @Param("senderId") senderId: Long,
        @Param("receiverId") receiverId: Long,
        @Param("date") date: LocalDate
    ): Boolean

    @Query(
        """
        SELECT m
        FROM MessageJpaEntity m
        WHERE m.messageType = :messageType
        AND m.receiverId = :receiverId
        AND m.id < :cursorId
        AND m.isDeleted = false
        AND m.receivedAt IS NOT NULL
        AND (
            m.senderId NOT IN :blockedUserIds OR
            m.sendAt < (
                SELECT COALESCE(MAX(bu.createdAt), '9999-12-31T23:59:59')
                FROM BlockedUserJpaEntity bu
                WHERE bu.blockerId = :receiverId AND bu.blockedId = m.senderId
            )
        )
        ORDER BY m.receivedAt DESC, m.id DESC
    """
    )
    fun findAllByMessageTypeAndReceiverIdAfterCursor(
        @Param("messageType") messageType: MessageType,
        @Param("receiverId") receiverId: Long,
        @Param("cursorId") cursorId: Long,
        @Param("blockedUserIds") blockedUserIds: List<Long>,
        pageable: Pageable
    ): List<MessageJpaEntity>

    @Query(
        """
        SELECT m FROM MessageJpaEntity m
        WHERE m.messageType = :messageType
        AND m.senderId = :senderId
        AND m.id < :cursorId
        AND m.isDeleted = false
        ORDER BY m.sendAt DESC, m.id DESC
    """
    )
    fun findAllMessagesByTypeAndSenderAfterCursor(
        @Param("messageType") messageType: MessageType,
        @Param("senderId") senderId: Long,
        @Param("cursorId") cursorId: Long,
        pageable: Pageable
    ): List<MessageJpaEntity>


    fun existsByIdAndSenderIdAndReceiverId(id: Long, senderId: Long, receiverId: Long): Boolean

    @Query(
        """
        SELECT COUNT(m)
        FROM MessageJpaEntity m
        WHERE m.messageType = :messageType
        AND m.receiverId = :receiverId
        AND m.id < :cursorId
        AND m.isDeleted = false
        AND (
            m.senderId NOT IN :blockedUserIds OR
            m.sendAt < (
                SELECT COALESCE(MAX(bu.createdAt), '9999-12-31T23:59:59')
                FROM BlockedUserJpaEntity bu
                WHERE bu.blockerId = :receiverId AND bu.blockedId = m.senderId
            )
        )
    """
    )
    fun countMessagesAfterCursor(
        @Param("messageType") messageType: MessageType,
        @Param("receiverId") receiverId: Long,
        @Param("cursorId") cursorId: Long,
        @Param("blockedUserIds") blockedUserIds: List<Long>
    ): Long

    @Query(
        """
        SELECT COUNT(m)
        FROM MessageJpaEntity m
        WHERE m.messageType = :messageType
        AND m.senderId = :senderId
        AND m.id < :cursorId
        AND m.isDeleted = false
    """
    )
    fun countSentMessagesAfterCursor(
        @Param("messageType") messageType: MessageType,
        @Param("senderId") senderId: Long,
        @Param("cursorId") cursorId: Long
    ): Long

    @Query(
        """
        SELECT m FROM MessageJpaEntity m
        WHERE m.messageType = :messageType
        AND m.senderId = :senderId
        AND m.isDeleted = false
        AND m.receivedAt IS NULL
        ORDER BY m.baseEntity.updatedAt DESC, m.id DESC
    """
    )
    fun findAllScheduledMessages(
        @Param("messageType") messageType: MessageType,
        @Param("senderId") senderId: Long
    ): List<MessageJpaEntity>

}

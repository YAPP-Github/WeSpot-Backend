package com.wespot.message

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate
import java.time.LocalDateTime

interface MessageJpaRepository : JpaRepository<MessageJpaEntity, Long> {

    @Query(
        """
        SELECT COUNT(m)
        FROM MessageJpaEntity m
        WHERE m.senderId = :senderId AND DATE(m.baseEntity.createdAt) = :date
        """
    )
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
    WHERE 1 = 1
    AND m.receiverId = :receiverId
    AND m.id < :cursorId
    AND m.messageType = 'RECEIVED'
    AND m.isReceiverDeleted = false
    AND m.receivedAt IS NOT NULL
    AND m.id NOT IN :blockedMessageIds
    AND m.senderId NOT IN :blockedUserIds
    """
    )
    fun findAllByMessageTypeAndReceiverIdAfterCursor(
        @Param("receiverId") receiverId: Long,
        @Param("cursorId") cursorId: Long,
        @Param("blockedUserIds") blockedUserIds: List<Long>,
        @Param("blockedMessageIds") blockedMessageIds: List<Long>,
        pageable: Pageable
    ): List<MessageJpaEntity>

    @Query(
        """
        SELECT m FROM MessageJpaEntity m
        WHERE 1 = 1
        AND m.senderId = :senderId
        AND m.id < :cursorId
        AND m.isSenderDeleted = false
        AND m.messageType = 'RECEIVED'
        ORDER BY m.sendAt DESC, m.id DESC
    """
    )
    fun findAllMessagesByTypeAndSenderAfterCursor(
        @Param("senderId") senderId: Long,
        @Param("cursorId") cursorId: Long,
        pageable: Pageable
    ): List<MessageJpaEntity>


    fun existsByIdAndSenderIdAndReceiverId(id: Long, senderId: Long, receiverId: Long): Boolean

    @Query(
        """
        SELECT COUNT(m)
        FROM MessageJpaEntity m
        WHERE 1 = 1
        AND m.receiverId = :receiverId
        AND m.id < :cursorId
        AND m.isReceiverDeleted = false
        AND  m.senderId NOT IN :blockedMessageIds
    """
    )
    fun countReceivedMessagesAfterCursor(
        @Param("receiverId") receiverId: Long,
        @Param("cursorId") cursorId: Long,
        @Param("blockedMessageIds") blockedMessageIds: List<Long>
    ): Long

    @Query(
        """
        SELECT COUNT(m)
        FROM MessageJpaEntity m
        WHERE 1 = 1
        AND m.senderId = :senderId
        AND m.id < :cursorId
        AND m.isSenderDeleted = false
    """
    )
    fun countSendMessagesAfterCursor(
        @Param("senderId") senderId: Long,
        @Param("cursorId") cursorId: Long
    ): Long

    @Query(
        """
        SELECT m FROM MessageJpaEntity m
        WHERE 1 = 1
        AND m.messageType = :messageType
        AND m.senderId = :senderId
        AND m.isSenderDeleted = false
        AND m.receivedAt IS NULL
        ORDER BY m.baseEntity.updatedAt DESC, m.id DESC
    """
    )
    fun findAllScheduledMessages(
        @Param("messageType") messageType: MessageType,
        @Param("senderId") senderId: Long
    ): List<MessageJpaEntity>

    @Query(
        """
        SELECT m FROM MessageJpaEntity m
        WHERE 1 = 1
        AND m.messageType = 'SENT'
        AND m.sendAt < :sendAt
        AND m.isSenderDeleted = false
    """
    )
    fun findByMessageTypeAndSendAtBefore(
        @Param("sendAt") sendAt: LocalDateTime
    ): List<MessageJpaEntity>

    @Query(
        """
        SELECT COUNT(m)
        FROM MessageJpaEntity m
        WHERE 1 = 1
            AND m.receiverId = :receiverId
            AND m.isReceiverRead = false
            AND m.messageType = 'RECEIVED'
            AND m.isReceiverDeleted = false
            AND m.receivedAt IS NOT NULL
            AND m.id NOT IN :blockedMessageIds
            AND m.receivedAt BETWEEN :sendTime AND :messageOpenTime
    """
    )
    fun countUnreadMessagesByReceiverIdAndBetweenSendTimeAndOpenTime(
        @Param("receiverId") receiverId: Long,
        @Param("blockedMessageIds") blockedMessageIds: List<Long>,
        @Param("sendTime") sendTime: LocalDateTime,
        @Param("messageOpenTime") messageOpenTime: LocalDateTime
    ): Long

}

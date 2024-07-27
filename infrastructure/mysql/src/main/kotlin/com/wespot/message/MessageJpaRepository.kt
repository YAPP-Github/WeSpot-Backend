package com.wespot.message

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate
import java.time.LocalDateTime

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

    @Query("""
        SELECT m
        FROM MessageJpaEntity m
        WHERE m.messageType = :messageType
        AND m.receiverId = :receiverId
        AND m.id < :cursorId
        ORDER BY m.receivedAt DESC, m.id DESC
    """)
    fun findAllByMessageTypeAndReceiverIdAfterCursor(
        @Param("messageType") messageType: MessageType,
        @Param("receiverId") receiverId: Long,
        @Param("cursorId") cursorId: Long,
        pageable: Pageable
    ): List<MessageJpaEntity>

    @Query(
        """
        SELECT m FROM MessageJpaEntity m
        WHERE m.messageType = :messageType
        AND m.senderId = :senderId
        AND m.id < :cursorId
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

}

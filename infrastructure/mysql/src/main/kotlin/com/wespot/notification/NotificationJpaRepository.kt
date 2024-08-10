package com.wespot.notification

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface NotificationJpaRepository : JpaRepository<NotificationJpaEntity, Long> {

    @Query(
        """
        SELECT n
        FROM NotificationJpaEntity n
        WHERE n.userId = :userId
        AND n.id < :cursorId
        ORDER BY n.baseEntity.createdAt DESC
        LIMIT :limit
    """
    )
    fun findAllByUserIdOrderByBaseEntityCreatedAtDesc(
        userId: Long,
        cursorId: Long,
        limit: Long
    ): List<NotificationJpaEntity>

    fun findAllByBaseEntityCreatedAtBetween(
        createdAtStart: LocalDateTime,
        createdAtEnd: LocalDateTime
    ): List<NotificationJpaEntity>

    fun findAllByUserIdAndBaseEntityCreatedAtBetween(
        userId: Long,
        createdAtStart: LocalDateTime,
        createdAtEnd: LocalDateTime
    ): List<NotificationJpaEntity>

}

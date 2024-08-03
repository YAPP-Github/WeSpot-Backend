package com.wespot.notification

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface NotificationJpaRepository : JpaRepository<NotificationJpaEntity, Long> {

    fun findAllByUserIdOrderByBaseEntityCreatedAtDesc(userId: Long): List<NotificationJpaEntity>

    fun findAllByBaseEntityCreatedAtBetween(
        createdAtStart: LocalDateTime,
        createdAtEnd: LocalDateTime
    ): List<NotificationJpaEntity>

    fun findAllByTargetId(targetId: Long): List<NotificationJpaEntity>

}

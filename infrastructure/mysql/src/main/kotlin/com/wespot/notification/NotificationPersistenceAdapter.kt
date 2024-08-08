package com.wespot.notification

import com.wespot.notification.port.out.NotificationPort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class NotificationPersistenceAdapter(
    private val notificationJpaRepository: NotificationJpaRepository
) : NotificationPort {

    override fun findAll(): List<Notification> {
        return notificationJpaRepository.findAll()
            .map { NotificationMapper.mapToDomainEntity(it) }
    }

    override fun save(notification: Notification): Notification {
        val savedNotification = notificationJpaRepository.save(NotificationMapper.mapToJpaEntity(notification))

        return NotificationMapper.mapToDomainEntity(savedNotification)
    }

    override fun saveAll(notifications: List<Notification>): List<Notification> {
        val notificationsJpaEntity = notifications.map { NotificationMapper.mapToJpaEntity(it) }

        return notificationJpaRepository.saveAll(notificationsJpaEntity)
            .map { NotificationMapper.mapToDomainEntity(it) }
    }

    override fun findById(id: Long): Notification? {
        return notificationJpaRepository.findByIdOrNull(id)
            ?.let { NotificationMapper.mapToDomainEntity(it) }
    }

    override fun findAllByUserIdOrderByCreatedAtDesc(userId: Long, cursorId: Long, limit: Long): List<Notification> {
        return notificationJpaRepository.findAllByUserIdOrderByBaseEntityCreatedAtDesc(userId, cursorId, limit)
            .map { NotificationMapper.mapToDomainEntity(it) }
    }

    override fun findAllFromDateYesterday(today: LocalDate): List<Notification> {
        val todayTime = today.atStartOfDay()
        val yesterdayTime = todayTime.minusDays(1)

        return notificationJpaRepository.findAllByBaseEntityCreatedAtBetween(yesterdayTime, todayTime)
            .map { NotificationMapper.mapToDomainEntity(it) }
    }

    override fun findAllFromDate(today: LocalDate): List<Notification> {
        val todayTime = today.atStartOfDay()
        val tomorrowTime = todayTime.plusDays(1)

        return notificationJpaRepository.findAllByBaseEntityCreatedAtBetween(todayTime, tomorrowTime)
            .map { NotificationMapper.mapToDomainEntity(it) }
    }

    override fun findAllByTargetId(targetId: Long): List<Notification> {
        return notificationJpaRepository.findAllByTargetId(targetId)
            .map { NotificationMapper.mapToDomainEntity(it) }
    }

}

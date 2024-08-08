package com.wespot.notification.port.out

import com.wespot.notification.Notification
import java.time.LocalDate

interface NotificationPort {

    fun findAll(): List<Notification>

    fun save(notification: Notification): Notification

    fun saveAll(notifications: List<Notification>): List<Notification>

    fun findById(id: Long): Notification?

    fun findAllByUserIdOrderByCreatedAtDesc(userId: Long, cursorId: Long, limit: Long): List<Notification>

    fun findAllFromDateYesterday(today: LocalDate): List<Notification>

    fun findAllFromDate(today: LocalDate): List<Notification>

    fun findAllByTargetId(targetId: Long): List<Notification>

}

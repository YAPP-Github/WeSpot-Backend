package com.wespot.notification.service

import com.wespot.notification.Notification
import com.wespot.notification.port.out.NotificationPort
import java.time.LocalDate

object NotificationFinder {

    fun findAll(notificationPort: NotificationPort): List<Notification> {
        return notificationPort.findAll()
    }

    fun findById(notificationPort: NotificationPort, id: Long): Notification {
        return notificationPort.findById(id)
            ?: throw IllegalArgumentException("ID에 해당하는 알림이 없습니다.")
    }

    fun findAllByUserIdOrderByCreatedAtDesc(
        notificationPort: NotificationPort,
        userId: Long,
        cursorId: Long?,
        limit: Long
    ): List<Notification> {
        return notificationPort.findAllByUserIdOrderByCreatedAtDesc(userId, cursorId, limit)
    }

    fun findAllFromDateYesterday(notificationPort: NotificationPort, today: LocalDate): List<Notification> {
        return notificationPort.findAllFromDateYesterday(today)
    }

    fun findAllFromDate(notificationPort: NotificationPort, today: LocalDate): List<Notification> {
        return notificationPort.findAllFromDate(today)
    }

    fun findAllByTargetId(notificationPort: NotificationPort, targetId: Long): List<Notification> {
        return notificationPort.findAllByTargetId(targetId)
    }

}

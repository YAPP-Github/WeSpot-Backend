package com.wespot.notification.service

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.notification.Notification
import com.wespot.notification.port.out.NotificationPort
import org.springframework.http.HttpStatus
import java.time.LocalDate

object NotificationFinder {

    fun findAll(notificationPort: NotificationPort): List<Notification> {
        return notificationPort.findAll()
    }

    fun findById(notificationPort: NotificationPort, id: Long): Notification {
        return notificationPort.findById(id)
            ?: throw CustomException(HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "ID에 해당하는 알림이 없습니다.")
    }

    fun findAllByUserIdOrderByCreatedAtDesc(
        notificationPort: NotificationPort,
        userId: Long,
        cursorId: Long,
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

    fun findAllByUserIdAndFromDate(
        notificationPort: NotificationPort,
        userId: Long,
        today: LocalDate
    ): List<Notification> {
        return notificationPort.findAllByUserIdAndFromDate(userId, today)
    }

}

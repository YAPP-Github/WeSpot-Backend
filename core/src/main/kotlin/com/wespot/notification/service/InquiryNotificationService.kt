package com.wespot.notification.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.notification.dto.NotificationResponses
import com.wespot.notification.port.`in`.InquiryNotificationUseCase
import com.wespot.notification.port.out.NotificationPort
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InquiryNotificationService(
    private val userPort: UserPort,
    private val notificationPort: NotificationPort
) : InquiryNotificationUseCase {

    @Transactional(readOnly = true)
    override fun getNotifications(cursorId: Long?, limit: Long): NotificationResponses {
        val loginUser = SecurityUtils.getLoginUser(userPort)
        val notifications =
            NotificationFinder.findAllByUserIdOrderByCreatedAtDesc(notificationPort, loginUser.id, cursorId, limit + 1)

        return NotificationResponses.from(notifications.take(limit.toInt()), notifications.size.toLong() == limit + 1)
    }

    @Transactional
    override fun readNotification(readNotificationId: Long) {
        val loginUser = SecurityUtils.getLoginUser(userPort)
        val notification = NotificationFinder.findById(notificationPort, readNotificationId)
        notification.read(loginUser.id)
        notificationPort.save(notification)
    }

}

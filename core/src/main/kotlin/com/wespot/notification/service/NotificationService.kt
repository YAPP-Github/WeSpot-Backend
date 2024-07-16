package com.wespot.notification.service

import com.wespot.notification.port.`in`.NotificationUseCase
import com.wespot.notification.port.out.NotificationStatePort

class NotificationService(
    private val notificationStatic: NotificationStatePort
) : NotificationUseCase {
}

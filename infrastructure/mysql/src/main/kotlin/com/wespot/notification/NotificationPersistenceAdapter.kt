package com.wespot.notification

import com.wespot.notification.port.out.NotificationStatePort
import org.springframework.stereotype.Repository

@Repository
class NotificationPersistenceAdapter(
    private val notificationJpaRepository: NotificationJpaRepository
) : NotificationStatePort {
}
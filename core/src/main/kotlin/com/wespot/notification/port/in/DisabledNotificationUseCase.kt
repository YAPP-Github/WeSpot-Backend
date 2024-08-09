package com.wespot.notification.port.`in`

import java.time.LocalDate

interface DisabledNotificationUseCase {

    fun disableVoteNotifications(today: LocalDate)

    fun disableMessageNotifications(today: LocalDate)

    fun disableMessageNotification(senderId: Long, sendMessageCount: Int)

}

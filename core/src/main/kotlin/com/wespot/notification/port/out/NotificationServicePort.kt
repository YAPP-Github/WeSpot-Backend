package com.wespot.notification.port.out

import com.wespot.notification.NotificationInfo
import com.wespot.user.User

interface NotificationServicePort {

    fun sendMulticastNotification(users: List<User>, notificationInfo: NotificationInfo)

    fun sendNotification(user: User, notificationInfo: NotificationInfo)

}

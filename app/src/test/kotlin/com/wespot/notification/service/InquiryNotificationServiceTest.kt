package com.wespot.notification.service

import com.wespot.notification.port.out.NotificationPort
import com.wespot.user.port.out.UserPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class InquiryNotificationServiceTest @Autowired constructor(
    private val userPort: UserPort,
    private val notificationPort: NotificationPort
) {

//    @Transactional(readOnly = true)
//    override fun getNotifications(): NotificationResponses {
//        val loginUser = SecurityUtils.getLoginUser(userPort)
//        val notifications = NotificationFinder.findAllByUserIdOrderByCreatedAtDesc(notificationPort, loginUser.id)
//
//        return NotificationResponses.from(notifications)
//    }
//
//    @Transactional
//    override fun readNotification(readNotificationId: Long) {
//        val loginUser = SecurityUtils.getLoginUser(userPort)
//        val notification = NotificationFinder.findById(notificationPort, readNotificationId)
//        notification.read(loginUser)
//        notificationPort.save(notification)
//    }

}

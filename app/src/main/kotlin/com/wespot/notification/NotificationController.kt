package com.wespot.notification

import com.wespot.notification.dto.NotificationResponses
import com.wespot.notification.port.`in`.InquiryNotificationUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/notifications")
class NotificationController(
    private val inquiryNotificationUseCase: InquiryNotificationUseCase
) {

    @GetMapping
    fun getNotifications(): ResponseEntity<NotificationResponses> {
        val notifications = inquiryNotificationUseCase.getNotifications()

        return ResponseEntity.ok(notifications)
    }

    @PatchMapping("{notificationId}")
    fun readNotification(@PathVariable notificationId: Long): ResponseEntity<Void> {
        inquiryNotificationUseCase.readNotification(notificationId)

        return ResponseEntity.noContent()
            .build()
    }

}

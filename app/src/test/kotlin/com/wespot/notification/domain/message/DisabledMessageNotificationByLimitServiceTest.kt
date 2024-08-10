package com.wespot.notification.domain.message

import com.wespot.notification.Notification
import com.wespot.notification.NotificationType
import com.wespot.notification.message.DisabledMessageNotificationByLimitService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class DisabledMessageNotificationByLimitServiceTest : BehaviorSpec({

    given("현재까지 보낸 메시지가") {
        val service = DisabledMessageNotificationByLimitService()
        val notifications = listOf(
            Notification.createMessageInitialState(1, NotificationType.MESSAGE, 1, "title", "body"),
            Notification.createMessageInitialState(1, NotificationType.MESSAGE_SENT, 1, "title", "body"),
            Notification.createMessageInitialState(1, NotificationType.MESSAGE_RECEIVED, 1, "title", "body"),
        )
        `when`("3개가 아니라면") {
            val disableMessageNotification = service.disableMessageNotification(2) { notifications }
            then("비활성화되지 않는다.") {
                disableMessageNotification.size shouldBe 3
                disableMessageNotification[0].isEnabled shouldBe true
                disableMessageNotification[1].isEnabled shouldBe true
                disableMessageNotification[2].isEnabled shouldBe true
            }
        }
        `when`("3개라면") {
            val disableMessageNotification = service.disableMessageNotification(3) { notifications }
            then("비활성화 된다.") {
                disableMessageNotification.size shouldBe 3
                disableMessageNotification[0].isEnabled shouldBe false
                disableMessageNotification[1].isEnabled shouldBe true
                disableMessageNotification[2].isEnabled shouldBe true
            }

            val enableMessageNotification = service.disableMessageNotification(2) { notifications }
            then("다시 3개 이하로 내려가면 활성화 된다.") {
                enableMessageNotification.size shouldBe 3
                enableMessageNotification[0].isEnabled shouldBe true
                enableMessageNotification[1].isEnabled shouldBe true
                enableMessageNotification[2].isEnabled shouldBe true
            }
        }
    }

})

package com.wespot.notification.domain.message

import com.wespot.notification.NotificationType
import com.wespot.notification.message.ReadMessageByReceiverService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class ReadMessageByReceiverServiceTest : BehaviorSpec({

    given("누군가가 쪽지를 읽었을 때") {
        val service = ReadMessageByReceiverService()
        `when`("처음 읽은 것이라면, 메시지를 보낸 이에게") {
            val notification = service.getNotification(1, "김재연", 1L, false)
            then("알림을 보낸다.") {
                notification!!.userId shouldBe 1
                notification.type shouldBe NotificationType.MESSAGE_SENT
                notification.targetId shouldBe 1
                notification.title shouldBe "방금 김재연님이 내가 보낸 쪽지를 읽었어요 \uD83E\uDEE2"
                notification.body shouldBe "앞으로도 에버가 큐피드가 되어 드릴게요"
            }
        }
        `when`("처음 읽은 것이 아니라면, 메시지를 보낸 이에게") {
            val notification = service.getNotification(1, "김재연", 1L, true)
            then("알림을 보내지 않는다.") {
                notification shouldBe null
            }
        }
    }

})

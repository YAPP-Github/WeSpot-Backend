package com.wespot.notification.domain

import com.wespot.notification.NotificationType
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class NotificationTypeTest : BehaviorSpec({

    given("알림 타입이") {
        `when`("Vote 타입인지") {
            then("확인한다.") {
                NotificationType.VOTE.isVote() shouldBe true
                NotificationType.VOTE_RESULT.isVote() shouldBe true
                NotificationType.VOTE_RECEIVED.isVote() shouldBe true
                NotificationType.MESSAGE.isVote() shouldBe false
                NotificationType.MESSAGE_SENT.isVote() shouldBe false
                NotificationType.MESSAGE_RECEIVED.isVote() shouldBe false
            }
        }
        `when`("Message 타입인지") {
            then("확인한다.") {
                NotificationType.VOTE.isMessage() shouldBe false
                NotificationType.VOTE_RESULT.isMessage() shouldBe false
                NotificationType.VOTE_RECEIVED.isMessage() shouldBe false
                NotificationType.MESSAGE.isMessage() shouldBe true
                NotificationType.MESSAGE_SENT.isMessage() shouldBe true
                NotificationType.MESSAGE_RECEIVED.isMessage() shouldBe true
            }
        }
    }

})

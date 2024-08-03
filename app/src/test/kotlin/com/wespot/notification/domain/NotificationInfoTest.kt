package com.wespot.notification.domain

import com.google.firebase.messaging.Notification
import com.wespot.notification.NotificationInfo
import com.wespot.notification.NotificationType
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkStatic
import java.time.LocalDate

class NotificationInfoTest : BehaviorSpec({

    given("NotificationInfo에서") {
        val now = LocalDate.now()
        val notificationInfo = NotificationInfo.createInitialState(
            com.wespot.notification.Notification.createVoteInitialState(
                0,
                NotificationType.VOTE,
                now,
                "title",
                "body"
            )
        )
        `when`("Notification을") {
            val notification = notificationInfo.getNotification()
            then("반환받는다.") {
                notification::class shouldBe Notification::class
            }
        }
        `when`("Data를") {
            val data = notificationInfo.getData()
            then("반환받는다.") {
                data["targetId"] shouldBe "0"
                data["date"] shouldBe now.toString()
                data["type"] shouldBe "VOTE"
            }
        }
    }

    given("NotificationInfo를 생성할 때") {
        mockkStatic(LocalDate::class)

        `when`("Vote용으로") {
            val now = LocalDate.now()
            val notification = com.wespot.notification.Notification.createVoteInitialState(
                0,
                NotificationType.VOTE,
                now,
                "title",
                "body"
            )
            val notificationInfo = NotificationInfo.createInitialState(notification)
            then("생성한다.") {
                notificationInfo.targetId shouldBe 0
                notificationInfo.date shouldBe now
                notificationInfo.type shouldBe NotificationType.VOTE
                notificationInfo.title shouldBe "title"
                notificationInfo.body shouldBe "body"
            }
        }
        `when`("Message용으로") {
            val now = LocalDate.now()
            every { LocalDate.now() } returns now
            val notification = com.wespot.notification.Notification.createMessageInitialState(
                0,
                NotificationType.MESSAGE,
                1,
                "title",
                "body"
            )
            val notificationInfo = NotificationInfo.createInitialState(notification)
            then("생성한다.") {
                notificationInfo.targetId shouldBe 1
                notificationInfo.date shouldBe now
                notificationInfo.type shouldBe NotificationType.MESSAGE
                notificationInfo.title shouldBe "title"
                notificationInfo.body shouldBe "body"
            }
        }
    }

})

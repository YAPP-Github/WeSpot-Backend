package com.wespot.notification.domain

import com.wespot.notification.Notification
import com.wespot.notification.NotificationType
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import java.time.LocalDate
import java.time.LocalDateTime

class NotificationTest : BehaviorSpec({

    given("알림을 생성할 때") {
        val now = LocalDate.now()
        mockkStatic(LocalDate::class)
        every { LocalDate.now() } returns now

        `when`("쪽지용으로") {
            val notifications = listOf(
                Notification.createMessageInitialState(
                    userId = 1,
                    type = NotificationType.MESSAGE,
                    targetId = 1,
                    title = "테스트",
                    body = "테스트"
                ),
                Notification.createMessageInitialState(
                    userId = 1,
                    type = NotificationType.MESSAGE_SENT,
                    targetId = 1,
                    title = "테스트",
                    body = "테스트"
                ),
                Notification.createMessageInitialState(
                    userId = 1,
                    type = NotificationType.MESSAGE_RECEIVED,
                    targetId = 1,
                    title = "테스트",
                    body = "테스트"
                )
            )
            then("생성한다.") {
                notifications[0].userId shouldBe 1
                notifications[0].type shouldBe NotificationType.MESSAGE
                notifications[0].targetId shouldBe 1
                notifications[0].date shouldBe now
                notifications[0].title shouldBe "테스트"
                notifications[1].userId shouldBe 1
                notifications[1].type shouldBe NotificationType.MESSAGE_SENT
                notifications[1].targetId shouldBe 1
                notifications[1].date shouldBe now
                notifications[1].title shouldBe "테스트"
                notifications[2].userId shouldBe 1
                notifications[2].type shouldBe NotificationType.MESSAGE_RECEIVED
                notifications[2].targetId shouldBe 1
                notifications[2].date shouldBe now
                notifications[2].title shouldBe "테스트"
            }
        }
        `when`("쪽지용으로 생성할 때, 잘못된 타입을 입력하면") {
            val shouldThrow1 = shouldThrow<IllegalArgumentException> {
                Notification.createMessageInitialState(
                    userId = 1,
                    type = NotificationType.VOTE,
                    targetId = 1,
                    title = "테스트",
                    body = "테스트"
                )
            }
            val shouldThrow2 = shouldThrow<IllegalArgumentException> {
                Notification.createMessageInitialState(
                    userId = 1,
                    type = NotificationType.VOTE_RESULT,
                    targetId = 1,
                    title = "테스트",
                    body = "테스트"
                )
            }
            val shouldThrow3 = shouldThrow<IllegalArgumentException> {
                Notification.createMessageInitialState(
                    userId = 1,
                    type = NotificationType.VOTE_RECEIVED,
                    targetId = 1,
                    title = "테스트",
                    body = "테스트"
                )
            }
            then("예외가 발생한다.") {
                shouldThrow1 shouldHaveMessage "쪽지 관련 알림이 아닙니다."
                shouldThrow2 shouldHaveMessage "쪽지 관련 알림이 아닙니다."
                shouldThrow3 shouldHaveMessage "쪽지 관련 알림이 아닙니다."
            }
        }

        `when`("투표용으로") {
            val notifications = listOf(
                Notification.createVoteInitialState(
                    userId = 1,
                    type = NotificationType.VOTE,
                    date = LocalDate.now().minusDays(1),
                    title = "테스트",
                    body = "테스트"
                ),
                Notification.createVoteInitialState(
                    userId = 1,
                    type = NotificationType.VOTE_RESULT,
                    date = LocalDate.now().minusDays(1),
                    title = "테스트",
                    body = "테스트"
                ),
                Notification.createVoteInitialState(
                    userId = 1,
                    type = NotificationType.VOTE_RECEIVED,
                    date = LocalDate.now().minusDays(1),
                    title = "테스트",
                    body = "테스트"
                )
            )

            then("생성한다.") {
                notifications[0].userId shouldBe 1
                notifications[0].type shouldBe NotificationType.VOTE
                notifications[0].targetId shouldBe 0
                notifications[0].date shouldBe LocalDate.now().minusDays(1)
                notifications[0].title shouldBe "테스트"
                notifications[1].userId shouldBe 1
                notifications[1].type shouldBe NotificationType.VOTE_RESULT
                notifications[1].targetId shouldBe 0
                notifications[1].date shouldBe LocalDate.now().minusDays(1)
                notifications[1].title shouldBe "테스트"
                notifications[2].userId shouldBe 1
                notifications[2].type shouldBe NotificationType.VOTE_RECEIVED
                notifications[2].targetId shouldBe 0
                notifications[2].date shouldBe LocalDate.now().minusDays(1)
                notifications[2].title shouldBe "테스트"
            }
        }

        `when`("투표용으로 생성할 때, 잘못된 타입을 입력하면") {
            val shouldThrow1 = shouldThrow<IllegalArgumentException> {
                Notification.createVoteInitialState(
                    userId = 1,
                    type = NotificationType.MESSAGE,
                    date = LocalDate.now(),
                    title = "테스트",
                    body = "테스트"
                )
            }
            val shouldThrow2 = shouldThrow<IllegalArgumentException> {
                Notification.createVoteInitialState(
                    userId = 1,
                    type = NotificationType.MESSAGE_SENT,
                    date = LocalDate.now(),
                    title = "테스트",
                    body = "테스트"
                )
            }
            val shouldThrow3 = shouldThrow<IllegalArgumentException> {
                Notification.createVoteInitialState(
                    userId = 1,
                    type = NotificationType.MESSAGE_RECEIVED,
                    date = LocalDate.now(),
                    title = "테스트",
                    body = "테스트"
                )
            }
            then("예외가 발생한다.") {
                shouldThrow1 shouldHaveMessage "투표 관련 알림이 아닙니다."
                shouldThrow2 shouldHaveMessage "투표 관련 알림이 아닙니다."
                shouldThrow3 shouldHaveMessage "투표 관련 알림이 아닙니다."
            }
        }
    }

    given("알림을") {
        val now = LocalDate.now()
        mockkStatic(LocalDate::class)
        val nowTime = LocalDateTime.now()
        mockkStatic(LocalDateTime::class)

        `when`("읽음 처리로") {
            every { LocalDateTime.now() } returns nowTime.minusDays(1)
            val notification = Notification.createVoteInitialState(
                userId = 1,
                type = NotificationType.VOTE,
                date = LocalDate.now(),
                title = "테스트",
                body = "테스트"
            )
            val beforeReadAt = notification.readAt
            every { LocalDateTime.now() } returns nowTime
            notification.read(1)
            then("변경한다.") {
                beforeReadAt shouldBe nowTime.minusDays(1)
                notification.isEnabled shouldBe true
                notification.isRead shouldBe true
                notification.readAt shouldBe nowTime
            }
        }
        `when`("읽음 처리로 변경할 때, 수신자가 요청한 것이 아니면") {
            val notification = Notification.createVoteInitialState(
                userId = 1,
                type = NotificationType.VOTE,
                date = LocalDate.now(),
                title = "테스트",
                body = "테스트"
            )
            val shouldThrow = shouldThrow<IllegalArgumentException> { notification.read(2) }
            then("예외가 발생한다.") {
                shouldThrow shouldHaveMessage "알림 수신자만 알림을 조회할 수 있습니다."
            }
        }
        `when`("투표용으로 생성한 뒤") {
            every { LocalDateTime.now() } returns nowTime.minusDays(1)
            val notification = Notification.createVoteInitialState(
                userId = 1,
                type = NotificationType.VOTE,
                date = LocalDate.now(),
                title = "테스트",
                body = "테스트"
            )
            notification.disableVoteNotification(now)
            then("비활성화한다.") {
                notification.isEnabled shouldBe false
            }
        }
        `when`("투표용으로 생성한 뒤, 쪽지용 비활성화를 실행하면") {
            val notification = Notification.createVoteInitialState(
                userId = 1,
                type = NotificationType.VOTE,
                date = LocalDate.now(),
                title = "테스트",
                body = "테스트"
            )
            notification.disableMessageNotification()
            then("활성 상태가 변경되지 않는다.") {
                notification.isEnabled shouldBe true
            }
        }
        `when`("Type을 VOTE가 아닌 타입으로 생성한 뒤, 투표용 비활성화를 실행하면") {
            every { LocalDateTime.now() } returns nowTime.minusDays(1)
            val notification1 = Notification.createVoteInitialState(
                userId = 1,
                type = NotificationType.VOTE_RESULT,
                date = now.minusDays(1),
                title = "테스트",
                body = "테스트"
            )
            val notification2 = Notification.createVoteInitialState(
                userId = 1,
                type = NotificationType.VOTE_RECEIVED,
                date = now.minusDays(1),
                title = "테스트",
                body = "테스트"
            )
            notification1.disableVoteNotification(now)
            notification2.disableVoteNotification(now)
            then("활성 상태가 변경되지 않는다.") {
                notification1.isEnabled shouldBe true
                notification2.isEnabled shouldBe true
            }
        }
        `when`("쪽지용으로 생성한 뒤") {
            val notification = Notification.createMessageInitialState(
                userId = 1,
                type = NotificationType.MESSAGE,
                targetId = 1,
                title = "테스트",
                body = "테스트"
            )
            notification.disableMessageNotification()
            then("비활성화한다.") {
                notification.isEnabled shouldBe false
            }
        }
        `when`("쪽지용으로 생성한 뒤, 투표용 비활성화를 실행하면") {
            val notification = Notification.createMessageInitialState(
                userId = 1,
                type = NotificationType.MESSAGE,
                targetId = 1,
                title = "테스트",
                body = "테스트"
            )
            notification.disableVoteNotification(now)
            then("활성 상태가 변경되지 않는다.") {
                notification.isEnabled shouldBe true
            }
        }
        `when`("Type을 MESSAGE가 아닌 타입으로 생성한 뒤, 쪽지용 비활성화를 실행하면") {
            val notification1 = Notification.createMessageInitialState(
                userId = 1,
                type = NotificationType.MESSAGE_SENT,
                targetId = 1,
                title = "테스트",
                body = "테스트"
            )
            val notification2 = Notification.createMessageInitialState(
                userId = 1,
                type = NotificationType.MESSAGE_SENT,
                targetId = 1,
                title = "테스트",
                body = "테스트"
            )
            notification1.disableMessageNotification()
            notification2.disableMessageNotification()

            then("활성 상태가 변경되지 않는다.") {
                notification1.isEnabled shouldBe true
                notification2.isEnabled shouldBe true
            }
        }

        unmockkStatic(LocalDate::class)
        unmockkStatic(LocalDateTime::class)
    }

})

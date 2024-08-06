package com.wespot.notification.domain

import com.wespot.notification.Notification
import com.wespot.notification.NotificationFilterService
import com.wespot.notification.NotificationType
import com.wespot.user.fixture.UserFixture
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.throwable.shouldHaveMessage
import java.time.LocalDate

class NotificationFilterTest : BehaviorSpec({

    given("단체 알림을") {
        val users = listOf(
            UserFixture.createWithId(1L),
            UserFixture.createWithId(2L),
            UserFixture.createWithId(3L),
            UserFixture.createWithId(4L),
            UserFixture.createWithId(5L)
        )
        val messageNotifications = users.map {
            Notification.createMessageInitialState(
                userId = it.id,
                title = "title",
                body = "body",
                targetId = 1,
                type = NotificationType.MESSAGE
            )
        }.toMutableList()
        val notificationFilterService = NotificationFilterService()
        `when`("메시지 알림 설정에 따라") {
            users[0].changeSettings(isEnableVoteNotification = false, isEnableMessageNotification = true)
            users[1].changeSettings(isEnableVoteNotification = false, isEnableMessageNotification = true)
            users[2].changeSettings(isEnableVoteNotification = false, isEnableMessageNotification = true)
            val filteredNotifications =
                notificationFilterService.filterNotifications(users, messageNotifications)
            then("필터링한다.") {
                filteredNotifications[0].userId shouldBe 1
                filteredNotifications[1].userId shouldBe 2
                filteredNotifications[2].userId shouldBe 3
            }
        }
        val voteNotifications = users.map {
            Notification.createVoteInitialState(
                userId = it.id,
                title = "title",
                body = "body",
                date = LocalDate.now(),
                type = NotificationType.VOTE
            )
        }
        `when`("투표 알림 설정에 따라") {
            users[0].changeSettings(isEnableVoteNotification = true, isEnableMessageNotification = false)
            users[1].changeSettings(isEnableVoteNotification = true, isEnableMessageNotification = false)
            users[2].changeSettings(isEnableVoteNotification = true, isEnableMessageNotification = false)
            val filteredNotifications =
                notificationFilterService.filterNotifications(users, voteNotifications)
            then("필터링한다.") {
                filteredNotifications[0].userId shouldBe 1
                filteredNotifications[1].userId shouldBe 2
                filteredNotifications[2].userId shouldBe 3
            }
        }
        `when`("서로 다른 알림 유형이 섞여있으면") {
            messageNotifications.add(
                Notification.createVoteInitialState(
                    userId = 1,
                    title = "title",
                    body = "body",
                    date = LocalDate.now(),
                    type = NotificationType.VOTE
                )
            )
            val shouldThrow = shouldThrow<IllegalArgumentException> {
                notificationFilterService.filterNotifications(
                    users,
                    messageNotifications
                )
            }
            then("예외를 발생시킨다.") {
                shouldThrow shouldHaveMessage "한번에 동일한 NotificationType만을 발송할 수 있습니다."
            }
        }
    }

    given("개별 알림을") {
        val user = UserFixture.createWithId(1L)
        val messageNotification = Notification.createMessageInitialState(
            userId = user.id,
            title = "title",
            body = "body",
            targetId = 1,
            type = NotificationType.MESSAGE
        )
        val notificationFilterService = NotificationFilterService()
        `when`("메시지 알림 설정에 따라") {
            user.changeSettings(isEnableVoteNotification = false, isEnableMessageNotification = true)
            val filterNotification1 =
                notificationFilterService.filterNotification(user, messageNotification)
            user.changeSettings(isEnableVoteNotification = false, isEnableMessageNotification = false)
            val filterNotification2 =
                notificationFilterService.filterNotification(user, messageNotification)
            then("필터링한다.") {
                filterNotification1 shouldNotBe null
                filterNotification2 shouldBe null
            }
        }
        val voteNotification = Notification.createVoteInitialState(
            userId = user.id,
            title = "title",
            body = "body",
            date = LocalDate.now(),
            type = NotificationType.VOTE
        )
        `when`("투표 알림 설정에 따라") {
            user.changeSettings(isEnableVoteNotification = true, isEnableMessageNotification = false)
            val filterNotification1 =
                notificationFilterService.filterNotification(user, voteNotification)
            user.changeSettings(isEnableVoteNotification = false, isEnableMessageNotification = false)
            val filterNotification2 =
                notificationFilterService.filterNotification(user, voteNotification)
            then("필터링한다.") {
                filterNotification1 shouldNotBe null
                filterNotification2 shouldBe null
            }
        }
    }

})

package com.wespot.notification.schedule

import com.wespot.notification.port.`in`.DisabledNotificationUseCase
import com.wespot.notification.port.`in`.MessageNotificationUseCase
import com.wespot.notification.port.`in`.VoteNotificationUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class NotificationScheduler(
    private val disabledNotificationUseCase: DisabledNotificationUseCase,
    private val voteNotificationUseCase: VoteNotificationUseCase,
    private val messageNotificationUseCase: MessageNotificationUseCase,
) {

    @Scheduled(cron = "0 0 0 * * *") // 매일 00시 실행
    fun disableVoteNotifications() {
        val today = LocalDate.now()
        disabledNotificationUseCase.disableVoteNotifications(today)
    }

    @Scheduled(cron = "0 0 22 * * *") // 매일 00시 실행
    fun disableMessageNotifications() {
        val today = LocalDate.now()
        disabledNotificationUseCase.disableMessageNotifications(today)
    }

    @Scheduled(cron = "0 0 9,15,21 * * *") // 매일 9, 15, 21시 실행
    fun encourageVote() {
        voteNotificationUseCase.encourageVote()
    }

    @Scheduled(cron = "0 0 17 * * *") // 매일 17시 실행
    fun openMessage() {
        messageNotificationUseCase.openMessage()
    }

}

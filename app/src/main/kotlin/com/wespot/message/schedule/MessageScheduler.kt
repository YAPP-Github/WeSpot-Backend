package com.wespot.message.schedule

import com.wespot.message.port.`in`.SchedulerMessageUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class MessageScheduler(
    private val schedulerMessageUseCase: SchedulerMessageUseCase
) {

    @Scheduled(cron = "10 0 22 * * ?")
    fun scheduleMessageUpdate() {
        schedulerMessageUseCase.sendScheduledMessages()
    }

}

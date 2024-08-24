package com.wespot.message.schedule

import com.wespot.message.port.`in`.SchedulerMessageUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class MessageScheduler(
    private val schedulerMessageUseCase: SchedulerMessageUseCase
) {

    // 오후 10시에 스케줄러 실행
    @Scheduled(cron = "0 0 22 * * ?")
    fun scheduleMessageUpdate() {
        schedulerMessageUseCase.sendScheduledMessages()
    }

}

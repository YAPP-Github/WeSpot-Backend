package com.wespot.auth.schedule

import com.wespot.message.port.`in`.SchedulerUserWithdrawalUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class UserWithdrawalScheduler(
    private val schedulerUserWithdrawalUseCase: SchedulerUserWithdrawalUseCase
) {

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    fun completeUserWithdrawals() {
        schedulerUserWithdrawalUseCase.completeUserWithdrawals()
    }
}

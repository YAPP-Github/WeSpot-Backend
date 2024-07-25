package com.wespot.report.schedule

import com.wespot.report.port.`in`.RevokeRestrictionUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ReportScheduler(
    private val revokeRestrictionUseCase: RevokeRestrictionUseCase
) {

    @Scheduled(cron = "0 0 0 * * *") // TODO : 매일 00시에 실행되도록 설정했어요.
    fun revokeRestriction() {
        revokeRestrictionUseCase.revokeRestriction()
    }

}

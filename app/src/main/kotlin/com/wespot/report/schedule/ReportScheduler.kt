package com.wespot.report.schedule

import com.wespot.report.port.`in`.RevokeRestrictionUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class ReportScheduler(
    private val revokeRestrictionUseCase: RevokeRestrictionUseCase
) {

    @Scheduled(cron = "50 0 0 * * *") // 매일 00시 00분에 50초에 실행
    fun revokeRestriction() {
        val today = LocalDate.now()
        revokeRestrictionUseCase.revokeRestriction(today)
    }

}

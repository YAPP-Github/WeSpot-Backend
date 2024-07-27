package com.wespot.report.port.`in`

import java.time.LocalDate

interface RevokeRestrictionUseCase {

    fun revokeRestriction(today: LocalDate)

}

package com.wespot.report.service

import com.wespot.report.port.`in`.RevokeRestrictionUseCase
import com.wespot.user.User
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class RevokeRestrictionService(
    private val userPort: UserPort
) : RevokeRestrictionUseCase {

    @Transactional
    override fun revokeRestriction() {
        val users = userPort.findAll()
        val today = LocalDate.now()
        users.map { it.restrict(getRestrictionByDate(it, today)) }
            .forEach { userPort.save(it) }
    }

    private fun getRestrictionByDate(
        it: User,
        today: LocalDate
    ) = it.restriction
        .getCurrentRestrictionBasedOnTime(today)

}

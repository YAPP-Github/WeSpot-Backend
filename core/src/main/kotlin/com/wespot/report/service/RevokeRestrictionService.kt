package com.wespot.report.service

import com.wespot.report.port.`in`.RevokeRestrictionUseCase
import com.wespot.user.User
import com.wespot.user.port.out.RestrictionPort
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class RevokeRestrictionService(
    private val userPort: UserPort,
    private val restrictionPort: RestrictionPort
) : RevokeRestrictionUseCase {

    @Transactional
    override fun revokeRestriction(today: LocalDate) {
        val users = userPort.findAll()

        users.forEach { changeUserRestrictionByDate(it, today) }
    }

    private fun changeUserRestrictionByDate(user: User, today: LocalDate) {
        val newRestriction = user.getCurrentUserRestrictionBasedOnTime(today)
        user.restrict(newRestriction)
        restrictionPort.save(newRestriction)
    }

}

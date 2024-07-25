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

        users.forEach { changeUserRestrictionByDate(it, today) }
        users.forEach { userPort.save(it) }
    }

    private fun changeUserRestrictionByDate(user: User, today: LocalDate) {
        val newRestriction = user.getCurrentUserRestrictionBasedOnTime(today)
        user.restrict(newRestriction)
    }

}

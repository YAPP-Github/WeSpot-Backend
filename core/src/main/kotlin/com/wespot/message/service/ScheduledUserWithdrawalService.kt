package com.wespot.message.service

import com.wespot.auth.PersonalInfo
import com.wespot.auth.port.out.PersonalInfoPort
import com.wespot.message.port.`in`.SchedulerUserWithdrawalUseCase
import com.wespot.user.Profile
import com.wespot.user.User
import com.wespot.user.WithdrawalStatus
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class ScheduledUserWithdrawalService(
    private val userPort: UserPort,
    private val personalInfoPort: PersonalInfoPort
): SchedulerUserWithdrawalUseCase {

    override fun completeUserWithdrawals() {

        val fifteenDaysAgo = LocalDateTime.now().minusDays(15)
        val usersToWithdraw = userPort.findAllByWithdrawalRequestAtBeforeAndWithdrawalStatus(
            fifteenDaysAgo, WithdrawalStatus.ACTIVE
        )
        usersToWithdraw.forEach { user ->
            personalInfoPort.save(PersonalInfo.create(user))
            val createInit = Profile.createInit()
            userPort.save(user.completeWithdraw(createInit))
        }
    }


}

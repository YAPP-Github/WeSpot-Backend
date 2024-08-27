package com.wespot.user.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.user.dto.response.CheckedRestrictionResponse
import com.wespot.user.port.`in`.CheckedUserRestrictionUseCase
import com.wespot.user.port.out.UserPort
import com.wespot.user.restriction.RestrictionPriority
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CheckedUserRestrictionService(
    private val userPort: UserPort
) : CheckedUserRestrictionUseCase {

    @Transactional(readOnly = true)
    override fun getUserRestriction(): CheckedRestrictionResponse {
        val loginUser = SecurityUtils.getLoginUser(userPort)
        val userRestriction = RestrictionPriority.fromRestrictionPriority(loginUser)

        return CheckedRestrictionResponse.from(userRestriction)
    }

}

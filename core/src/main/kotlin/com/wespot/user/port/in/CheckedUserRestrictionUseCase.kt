package com.wespot.user.port.`in`

import com.wespot.user.dto.response.CheckedRestrictionResponse

interface CheckedUserRestrictionUseCase {

    fun getUserRestriction(): CheckedRestrictionResponse

}

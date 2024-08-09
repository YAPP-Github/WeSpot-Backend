package com.wespot.common.`in`

import com.wespot.common.dto.CheckProfanityRequest

interface CheckProfanityUseCase {

    fun checkProfanity(checkProfanityRequest: CheckProfanityRequest)

}

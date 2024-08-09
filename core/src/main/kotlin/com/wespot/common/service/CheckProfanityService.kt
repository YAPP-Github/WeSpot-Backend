package com.wespot.common.service

import com.wespot.common.ProfanityChecker
import com.wespot.common.dto.CheckProfanityRequest
import com.wespot.common.`in`.CheckProfanityUseCase
import org.springframework.stereotype.Service

@Service
class CheckProfanityService : CheckProfanityUseCase {

    override fun checkProfanity(checkProfanityRequest: CheckProfanityRequest) {
        ProfanityChecker.validateContent(checkProfanityRequest.message)
    }

}

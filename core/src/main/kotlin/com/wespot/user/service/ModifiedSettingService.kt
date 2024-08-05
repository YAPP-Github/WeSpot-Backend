package com.wespot.user.service

import com.wespot.user.dto.request.ModifiedSettingRequest
import com.wespot.user.port.`in`.ModifiedSettingUseCase
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service

@Service
class ModifiedSettingService(
    private val userPort: UserPort
) : ModifiedSettingUseCase {

    override fun modifySetting(modifiedSettingRequest: ModifiedSettingRequest) {
        TODO("Not yet implemented")
    }

}

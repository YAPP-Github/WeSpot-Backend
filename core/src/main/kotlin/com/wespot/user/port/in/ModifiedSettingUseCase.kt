package com.wespot.user.port.`in`

import com.wespot.user.dto.request.ModifiedSettingRequest

interface ModifiedSettingUseCase {

    fun modifySetting(modifiedSettingRequest: ModifiedSettingRequest)

}

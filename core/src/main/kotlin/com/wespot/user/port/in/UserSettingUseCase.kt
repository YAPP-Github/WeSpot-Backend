package com.wespot.user.port.`in`

import com.wespot.user.dto.request.ModifiedSettingRequest
import com.wespot.user.dto.response.UserSettingResponse

interface UserSettingUseCase {

    fun modifySetting(modifiedSettingRequest: ModifiedSettingRequest)

    fun getSetting(): UserSettingResponse

}

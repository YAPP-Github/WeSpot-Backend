package com.wespot.user.port.`in`

import com.wespot.user.dto.request.MessageV2Setting

interface ModifyMessageV2SettingUseCase {

    fun changeSetting(
        toChangeSetting: MessageV2Setting,
    )

}

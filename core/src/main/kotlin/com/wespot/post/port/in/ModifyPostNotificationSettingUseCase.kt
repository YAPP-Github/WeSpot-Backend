package com.wespot.post.port.`in`

import com.wespot.post.dto.request.ModifyPostNotificationSettingRequest


interface ModifyPostNotificationSettingUseCase {

    fun changeSetting(modifyPostNotificationSettingRequest: ModifyPostNotificationSettingRequest)

}

package com.wespot.post

import com.wespot.post.dto.request.ModifyPostNotificationSettingRequest
import com.wespot.post.port.`in`.ModifyPostNotificationSettingUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v2/post")
class ModifyPostNotificationSettingController(
    private val modifyPostNotificationSettingUseCase: ModifyPostNotificationSettingUseCase
) {

    @PatchMapping("/notification-setting")
    fun modifyPostNotificationSetting(
        @RequestBody modifyPostNotificationSettingRequest: ModifyPostNotificationSettingRequest
    ): ResponseEntity<Unit> {
        modifyPostNotificationSettingUseCase.changeSetting(modifyPostNotificationSettingRequest = modifyPostNotificationSettingRequest)

        return ResponseEntity.noContent()
            .build()
    }

}

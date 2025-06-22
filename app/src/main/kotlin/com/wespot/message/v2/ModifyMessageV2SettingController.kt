package com.wespot.message.v2

import com.wespot.user.dto.request.MessageV2Setting
import com.wespot.user.port.`in`.ModifyMessageV2SettingUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v2/messages")
class ModifyMessageV2SettingController(
    private val modifyMessageV2SettingUseCase: ModifyMessageV2SettingUseCase
) {

    @PatchMapping("/setting")
    fun answerMessage(
        @RequestBody messageV2Setting: MessageV2Setting
    ): ResponseEntity<Unit> {
        modifyMessageV2SettingUseCase.changeSetting(messageV2Setting)

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .build()
    }

}

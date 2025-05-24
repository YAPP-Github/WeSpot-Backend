package com.wespot.message.v2

import com.wespot.message.dto.response.TitleOfMessageV2Response
import com.wespot.message.port.`in`.GetTitleOfMessageV2UseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v2/messages")
class GetTitleOfMessageV2Controller(
    private val getTitleOfMessageV2UseCase: GetTitleOfMessageV2UseCase,
) {

    @GetMapping("/title")
    fun getTitleOfMessage(): ResponseEntity<TitleOfMessageV2Response> {
        val response = getTitleOfMessageV2UseCase.getTitle()

        return ResponseEntity.ok(response)
    }

}

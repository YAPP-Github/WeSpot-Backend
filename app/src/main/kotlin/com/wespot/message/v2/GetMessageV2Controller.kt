package com.wespot.message.v2

import com.wespot.message.dto.response.MessageV2DetailsResponse
import com.wespot.message.dto.response.MessageV2OverviewResponse
import com.wespot.message.port.`in`.GetMessageV2UseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v2/messages")
class GetMessageV2Controller(
    private val getMessageV2UseCase: GetMessageV2UseCase
) {

    @GetMapping
    fun getMessagesOverview(): ResponseEntity<List<MessageV2OverviewResponse>> {
        val response = getMessageV2UseCase.getMessageOverview()

        return ResponseEntity.ok(response)
    }

    @GetMapping("/bookmarks")
    fun getBookmarkedMessagesOverview(): ResponseEntity<List<MessageV2OverviewResponse>> {
        val response = getMessageV2UseCase.getBookmarkedMessageOverview()

        return ResponseEntity.ok(response)
    }

    @GetMapping("/{messageId}/details")
    fun getMessageDetails(@PathVariable messageId: Long): ResponseEntity<MessageV2DetailsResponse> {
        val response = getMessageV2UseCase.getMessageDetails(messageId)

        return ResponseEntity.ok(response)
    }
}

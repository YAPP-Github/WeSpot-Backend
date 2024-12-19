package com.wespot.admin

import com.wespot.admin.dto.CreatedVoteOptionRequest
import com.wespot.admin.dto.UpdateVoteOptionRequest
import com.wespot.admin.dto.VoteOptionResponses
import com.wespot.admin.port.`in`.AdminVoteOptionUseCase
import com.wespot.admin.swagger.AdminSwagger
import com.wespot.notification.dto.NotificationPublishingRequest
import com.wespot.notification.port.`in`.PublishNotificationUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin")
class AdminController(
    private val adminVoteOptionUseCase: AdminVoteOptionUseCase,
    private val publishNotificationUseCase: PublishNotificationUseCase
) : AdminSwagger {

    @GetMapping("/vote-options")
    override fun getVoteOptions(): ResponseEntity<VoteOptionResponses> {
        val responses = adminVoteOptionUseCase.getVoteOptions()

        return ResponseEntity.ok(responses)
    }

    @PostMapping("/vote-options")
    override fun createVoteOption(
        @RequestBody request: CreatedVoteOptionRequest
    ): ResponseEntity<Long> {
        val response = adminVoteOptionUseCase.createVoteOption(request)

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(response)
    }

    @PostMapping("/vote-options/bulks")
    override fun createVoteOptions(
        @RequestBody requests: List<CreatedVoteOptionRequest>
    ): ResponseEntity<List<Long>> {
        val response = adminVoteOptionUseCase.createVoteOptions(requests)

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(response)
    }

    @PutMapping("/vote-options/{vote-option-id}")
    override fun updateVoteOption(
        @PathVariable("vote-option-id") voteOptionId: Long,
        @RequestBody request: UpdateVoteOptionRequest
    ): ResponseEntity<Long> {
        val response = adminVoteOptionUseCase.updateVoteOption(voteOptionId, request)

        return ResponseEntity.ok(response)
    }

    @PostMapping("/push-notification/publish")
    override fun publishNotification(
        @RequestBody notificationPublishingRequest: NotificationPublishingRequest
    ): ResponseEntity<Unit> {
        publishNotificationUseCase.publishProfileUpdate(notificationPublishingRequest)

        return ResponseEntity.status(HttpStatus.CREATED)
            .build()
    }

    @GetMapping("") // Notific
    override fun getRemoteConfigsVariable(): ResponseEntity<List<RemoteConfigVariableResponse>> {

        publishNotificationUseCase.publishProfileUpdate(notificationPublishingRequest)

        return ResponseEntity.status(HttpStatus.CREATED)
            .build()
    }

    @PutMapping("") // Notific
    override fun getNotification(): ResponseEntity<Unit> {
        publishNotificationUseCase.publishProfileUpdate(notificationPublishingRequest)

        return ResponseEntity.status(HttpStatus.CREATED)
            .build()
    }

}

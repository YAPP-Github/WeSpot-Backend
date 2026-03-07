package com.wespot.admin

import com.wespot.admin.dto.*
import com.wespot.admin.port.`in`.AdminUserDeletionUseCase
import com.wespot.admin.port.`in`.AdminUserUseCase
import com.wespot.admin.port.`in`.AdminVoteOptionUseCase
import com.wespot.admin.port.`in`.FirebaseUseCase
import com.wespot.admin.swagger.AdminSwagger
import com.wespot.notification.dto.NotificationPublishingRequest
import com.wespot.notification.dto.PublishNotificationTypeResponse
import com.wespot.notification.port.`in`.PublishNotificationUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
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
    private val publishNotificationUseCase: PublishNotificationUseCase,
    private val firebaseUseCase: FirebaseUseCase,
    private val adminUserUseCase: AdminUserUseCase,
    private val adminUserDeletionUseCase: AdminUserDeletionUseCase,
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

    @GetMapping("/push-notification/publish")
    override fun getNotificationPublishingScreen(): ResponseEntity<List<PublishNotificationTypeResponse>> {
        val response = publishNotificationUseCase.viewAllOfPossibleToPublishTypes()

        return ResponseEntity.ok(response)
    }

    @PostMapping("/push-notification/publish")
    override fun publishNotification(
        @RequestBody notificationPublishingRequest: NotificationPublishingRequest
    ): ResponseEntity<Unit> {
        publishNotificationUseCase.publish(notificationPublishingRequest)

        return ResponseEntity.status(HttpStatus.CREATED)
            .build()
    }

    @GetMapping("/remote-config")
    override fun getRemoteConfigsVariables(): ResponseEntity<List<RemoteConfigVariableResponse>> {
        val response = firebaseUseCase.findAllVariableInRemoteConfig()

        return ResponseEntity.ok(response)
    }

    @PutMapping("/remote-config")
    override fun setRemoteConfigVariables(@RequestBody request: ModifiedRemoteConfigVariableRequest): ResponseEntity<Unit> {
        firebaseUseCase.modifyRemoteConfigVariables(request)

        return ResponseEntity.status(HttpStatus.OK)
            .build()
    }

    @PostMapping("/remote-config")
    override fun addRemoteConfigVariables(@RequestBody request: SavedRemoteConfigVariableRequest): ResponseEntity<Unit> {
        firebaseUseCase.addRemoteConfigVariables(request)

        return ResponseEntity.status(HttpStatus.CREATED)
            .build()
    }

    @DeleteMapping("/remote-config/{key}")
    override fun deleteRemoteConfigVariables(@PathVariable key: String): ResponseEntity<Unit> {
        firebaseUseCase.remoteRemoteConfigVariables(key)

        return ResponseEntity.noContent()
            .build()
    }

    @GetMapping("/users")
    override fun getAllUsers(): ResponseEntity<List<AdminUserResponse>> {
        val response = adminUserUseCase.getAllUsers()

        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/users/{userId}")
    override fun deleteUser(
        @PathVariable userId: Long,
    ): ResponseEntity<Unit> {
        adminUserDeletionUseCase.deleteUser(userId)

        return ResponseEntity.noContent().build()
    }

}

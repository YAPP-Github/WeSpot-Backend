package com.wespot.admin.swagger

import com.wespot.admin.dto.*
import com.wespot.notification.dto.NotificationPublishingRequest
import com.wespot.notification.dto.PublishNotificationTypeResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Admin API", description = "혜연짱을 위한 API 입니다.")
interface AdminSwagger {

    @Operation(summary = "선택지 조회 API")
    fun getVoteOptions(): ResponseEntity<VoteOptionResponses>

    @Operation(summary = "선택지 생성 API")
    fun createVoteOption(
        request: CreatedVoteOptionRequest
    ): ResponseEntity<Long>

    @Operation(summary = "선택지 벌크 생성 API")
    fun createVoteOptions(
        requests: List<CreatedVoteOptionRequest>
    ): ResponseEntity<List<Long>>

    @Operation(summary = "선택지 업데이트 API")
    fun updateVoteOption(
        @Parameter(required = true) voteOptionId: Long,
        request: UpdateVoteOptionRequest
    ): ResponseEntity<Long>

    @Operation(summary = "푸시 알림 타입을 조회할 수 있는 API")
    fun getNotificationPublishingScreen(): ResponseEntity<List<PublishNotificationTypeResponse>>

    @Operation(summary = "푸시 알림 전송 API")
    fun publishNotification(
        notificationPublishingRequest: NotificationPublishingRequest
    ): ResponseEntity<Unit>

    @Operation(summary = "Remote Config 변수 목록 조회 API")
    fun getRemoteConfigsVariables(): ResponseEntity<List<RemoteConfigVariableResponse>>

    @Operation(summary = "Remote Config 변수 수정 API")
    fun setRemoteConfigVariables(request: ModifiedRemoteConfigVariableRequest): ResponseEntity<Unit>

    @Operation(summary = "Remote Config 변수 생성 API")
    fun addRemoteConfigVariables(request: SavedRemoteConfigVariableRequest): ResponseEntity<Unit>

    @Operation(summary = "Remote Config 변수 삭제 API")
    fun deleteRemoteConfigVariables(key: String): ResponseEntity<Unit>

}

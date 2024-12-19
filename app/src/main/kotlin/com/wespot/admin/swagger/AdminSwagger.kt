package com.wespot.admin.swagger

import com.wespot.admin.dto.CreatedVoteOptionRequest
import com.wespot.admin.dto.UpdateVoteOptionRequest
import com.wespot.admin.dto.VoteOptionResponses
import com.wespot.notification.dto.NotificationPublishingRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

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

    @Operation(summary = "푸시 알림 전송 API")
    fun publishNotification(
        @RequestBody notificationPublishingRequest: NotificationPublishingRequest
    ): ResponseEntity<Unit>

}

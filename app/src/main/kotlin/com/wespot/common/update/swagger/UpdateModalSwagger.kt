package com.wespot.common.update.swagger

import com.wespot.common.dto.UpdatedModalComponentResponse
import com.wespot.notification.NotificationType
import com.wespot.notification.PublishNotificationType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity

@Tag(name = "Update Modal API", description = "Update Modal API 관련 문서입니다.")
interface UpdateModalSwagger {

    @Operation(summary = "알림을 클라이언트에서 넘겨주면 그에 맞는 업데이트 모달 UI를 반환합니다.")
    fun getProfileUpdateScreen(publishNotificationType: PublishNotificationType): ResponseEntity<UpdatedModalComponentResponse>

}

package com.wespot.admin.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "선택지 업데이트 Request입니다.")
class UpdateVoteOptionRequest(
    @field:Schema(
        description = "업데이트할 선택지의 내용입니다.",
        example = "진호 엉덩이",
        type = "String",
    )
    val content: String
) {
}

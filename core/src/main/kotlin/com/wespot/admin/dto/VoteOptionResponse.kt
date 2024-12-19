package com.wespot.admin.dto

import com.wespot.voteoption.VoteOption
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "선택지 Response입니다.")
data class VoteOptionResponse(
    @field:Schema(
        description = "선택지의 ID입니다.",
        example = "1",
        type = "Long",
    )
    val id: Long,
    @field:Schema(
        description = "선택지의 내용입니다.",
        example = "진호 엉덩이",
        type = "String",
    )
    val content: String,
    @field:Schema(
        description = "해당 선택지의 생성 시간입니다.",
        example = "2021-08-01T00:00:00",
        type = "String",
    )
    val createdAt: String,
    @field:Schema(
        description = "해당 선택지의 업데이트 시간입니다.",
        example = "2021-08-01T00:00:00",
        type = "String",
    )
    val updatedAt: String,
) {

    companion object {
        fun from(
            voteOption: VoteOption
        ): VoteOptionResponse {
            return VoteOptionResponse(
                id = voteOption.id,
                content = voteOption.content.content,
                createdAt = voteOption.createdAt.toString(),
                updatedAt = voteOption.updatedAt.toString()
            )
        }
    }

}

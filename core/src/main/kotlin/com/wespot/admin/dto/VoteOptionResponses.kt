package com.wespot.admin.dto

import com.wespot.voteoption.VoteOption
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "선택지 목록 Response입니다.")
data class VoteOptionResponses(
    @field:Schema(
        description = "선택지 목록입니다.",
    )
    val voteOptions: List<VoteOptionResponse>
) {

    companion object {
        fun from(voteOptions: List<VoteOption>): VoteOptionResponses {
            return VoteOptionResponses(
                voteOptions.map { VoteOptionResponse.from(it) }
            )
        }
    }

}

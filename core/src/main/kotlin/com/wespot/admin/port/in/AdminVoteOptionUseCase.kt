package com.wespot.admin.port.`in`

import com.wespot.admin.dto.CreatedVoteOptionRequest
import com.wespot.admin.dto.UpdateVoteOptionRequest
import com.wespot.admin.dto.VoteOptionResponses

interface AdminVoteOptionUseCase {

    fun getVoteOptions(): VoteOptionResponses

    fun createVoteOption(request: CreatedVoteOptionRequest): Long

    fun createVoteOptions(requests: List<CreatedVoteOptionRequest>): List<Long>

    fun updateVoteOption(voteOptionId: Long, request: UpdateVoteOptionRequest): Long


}

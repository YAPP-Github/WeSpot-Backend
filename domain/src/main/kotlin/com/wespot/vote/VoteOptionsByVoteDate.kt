package com.wespot.vote

import com.wespot.voteoption.VoteOption
import java.time.LocalDate

data class VoteOptionsByVoteDate(
    val voteDate: LocalDate,
    val voteOptions: List<VoteOption>
) {

    companion object {
        fun of(date: LocalDate, voteOptions: List<VoteOption>): VoteOptionsByVoteDate {
            if (voteOptions.size != 5) {
                throw IllegalArgumentException("선택지는 5개가 주어져야 합니다.")
            }

            return VoteOptionsByVoteDate(date, voteOptions)
        }
    }

    fun validateVoteOption(
        voteOptionId: Long
    ) {
        if (voteOptions.find { it.id == voteOptionId } != null) {
            return
        }
        throw IllegalArgumentException("오늘 제공된 질문지만 선택해 투표할 수 있습니다.")
    }

}

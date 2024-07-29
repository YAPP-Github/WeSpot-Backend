package com.wespot.vote

import com.wespot.voteoption.VoteOption
import java.time.LocalDate

data class VoteOptionsByVoteDate(
    val voteDate: LocalDate,
    val voteOptionsByVoteDate: List<VoteOptionByVoteDate>,
) {

    companion object {

        private const val NUMBER_OF_VOTE_OPTIONS = 5

        fun createInitialVoteOptionsByVoteDate(
            voteId: Long,
            date: LocalDate,
            voteNumber: Int,
            allVoteOptions: List<VoteOption>
        ): VoteOptionsByVoteDate {
            validateDate(date)
            validateVoteOptionsSize(allVoteOptions.size)
            val voteOptionIndex: Int = (voteNumber * NUMBER_OF_VOTE_OPTIONS) % allVoteOptions.size
            validateVoteOptionsSizeMultipleOf5(allVoteOptions.size, voteOptionIndex)
            val voteOptionsByVoteDate =
                allVoteOptions.subList(voteOptionIndex, voteOptionIndex + NUMBER_OF_VOTE_OPTIONS)

            return VoteOptionsByVoteDate(
                voteDate = date,
                voteOptionsByVoteDate
                    .map { voteOption -> VoteOptionByVoteDate(0, voteId, voteOption) }
            )
        }

        private fun validateDate(date: LocalDate) {
            require(LocalDate.now() >= date) { throw IllegalArgumentException("미래의 선택지는 정할 수 없습니다.") }
        }

        private fun validateVoteOptionsSize(allVoteOptionsSize: Int) {
            require(allVoteOptionsSize >= 5) { throw IllegalArgumentException("선택지는 최소 5개 이상이어야 합니다.") }
        }

        private fun validateVoteOptionsSizeMultipleOf5(
            allVoteOptionsSize: Int,
            voteOptionIndex: Int
        ) {
            require(allVoteOptionsSize >= voteOptionIndex + NUMBER_OF_VOTE_OPTIONS) {
                throw IllegalArgumentException("선택지의 개수가 5의 배수가 아닙니다.")
            }
        }

    }

    fun validateVoteOption(
        voteOptionId: Long
    ) {
        require(voteOptionsByVoteDate.find { it.containVoteOptionInVoteOptionByVoteDate(voteOptionId) } != null) {
            throw IllegalArgumentException("오늘 제공된 질문지만 선택해 투표할 수 있습니다.")
        }
    }

}

package com.wespot.vote

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.voteoption.VoteOption
import org.springframework.http.HttpStatus
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
            val voteOptionsByVoteDate = createVoteOptionsByDate(voteNumber, allVoteOptions)

            return VoteOptionsByVoteDate(
                voteDate = date,
                voteOptionsByVoteDate
                    .map { voteOption -> VoteOptionByVoteDate(0, voteId, voteOption) }
            )
        }

        private fun validateDate(date: LocalDate) {
            require(LocalDate.now() >= date) {
                throw CustomException(
                    HttpStatus.BAD_REQUEST,
                    ExceptionView.TOAST,
                    "미래의 선택지는 정할 수 없습니다."
                )
            }
        }

        private fun validateVoteOptionsSize(allVoteOptionsSize: Int) {
            require(allVoteOptionsSize >= NUMBER_OF_VOTE_OPTIONS) {
                throw CustomException(
                    HttpStatus.BAD_REQUEST,
                    ExceptionView.TOAST,
                    "선택지는 최소 ${NUMBER_OF_VOTE_OPTIONS}개 이상이어야 합니다."
                )
            }
        }

        private fun createVoteOptionsByDate(
            voteNumber: Int,
            allVoteOptions: List<VoteOption>
        ): MutableList<VoteOption> {
            var voteOptionIndex: Int = (voteNumber * NUMBER_OF_VOTE_OPTIONS) % allVoteOptions.size
            val voteOptionsByVoteDate = mutableListOf<VoteOption>()

            while (voteOptionsByVoteDate.size < NUMBER_OF_VOTE_OPTIONS) {
                voteOptionsByVoteDate.add(allVoteOptions[voteOptionIndex])
                voteOptionIndex = (voteOptionIndex + 1) % allVoteOptions.size
            }
            return voteOptionsByVoteDate
        }

    }

    fun validateVoteOption(
        voteOptionId: Long
    ) {
        require(voteOptionsByVoteDate.find { it.isSameVoteOption(voteOptionId) } != null) {
            throw CustomException(HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "오늘 제공된 질문지만 선택해 투표할 수 있습니다.")
        }
    }

}

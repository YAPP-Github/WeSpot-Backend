package com.wespot.vote

import com.wespot.user.User
import com.wespot.voteoption.VoteOption
import java.time.LocalDate

data class Vote(
    val id: Long?,
    val schoolId: Long,
    val grade: Int,
    val groupNumber: Int,
    val voteNumber: Int,
    val date: LocalDate,
    val ballots: Ballots,
) {

    companion object {
        private val NUMBER_OF_VOTE_USERS = 5
        private val NUMBER_OF_VOTE_OPTIONS = 5
        private val MOVE_TO_NEXT_VOTE_OPTION = 1
    }

    fun findTodayVoteOptions(voteOptions: List<VoteOption>): List<VoteOption> {
        validateVoteOptionsSize(voteOptions)
        val todayVoteOptions: MutableList<VoteOption> = mutableListOf()
        var voteOptionIndex: Int = (voteNumber * NUMBER_OF_VOTE_OPTIONS) % voteOptions.size
        while (todayVoteOptions.size < NUMBER_OF_VOTE_OPTIONS) {
            todayVoteOptions.add(voteOptions[voteOptionIndex])
            voteOptionIndex = (voteOptionIndex + MOVE_TO_NEXT_VOTE_OPTION) % voteOptions.size
        }

        return todayVoteOptions.toList()
    }

    private fun validateVoteOptionsSize(voteOptions: List<VoteOption>) {
        if (voteOptions.size < 5) {
            throw IllegalArgumentException("선택지는 최소 5개 이상이어야 합니다.")
        }
    }

    fun findUsersForVote(classmates: List<User>, user: User): List<User> {
        val alreadyVotedByUser: List<Long> = ballots.findUserIdsVotedByUser(user.id!!)
        return classmates.stream()
            .filter { !alreadyVotedByUser.contains(it.id) && isNotMe(it, user) }
            .toList()
//            .shuffled() // TODO: Shuffle 하게 되면, 사용자가 중간에 그만두었을 때에도 다른 결과를 반환할 것 같아서 지우려고요. 동의하시나요 ?!
            .take(NUMBER_OF_VOTE_USERS)
    }

    private fun isNotMe(classmate: User, user: User) = classmate != user

    fun addBallot(
        todayVoteOptionsIds: List<Long>,
        voteOptionId: Long,
        senderId: Long,
        receiverId: Long
    ) {
        validateVoteOption(todayVoteOptionsIds, voteOptionId)
        ballots.add(
            Ballot.of(
                voteId = id!!,
                voteOptionId = voteOptionId,
                senderId = senderId,
                receiverId = receiverId
            )
        )
    }

    private fun validateVoteOption(
        todayVoteOptionsIds: List<Long>,
        voteOptionId: Long
    ) {
        if (todayVoteOptionsIds.contains(voteOptionId)) {
            return
        }
        throw IllegalArgumentException("오늘 제공된 질문지만 선택해 투표할 수 있습니다.")
    }

    fun getBallots(): List<Ballot> {
        return ballots.ballots
            .map { it.value }
            .flatMap { it.values }
            .toList()
    }

}
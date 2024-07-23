package com.wespot.vote

import com.wespot.user.User
import com.wespot.voteoption.VoteOption
import java.time.LocalDate

data class Vote(
    val id: Long,
    val schoolId: Long,
    val grade: Int,
    val classNumber: Int,
    val voteNumber: Int,
    val date: LocalDate,
    val ballots: Ballots,
) {

    companion object {
        private val NUMBER_OF_VOTE_USERS = 5
        private val NUMBER_OF_VOTE_OPTIONS = 5
    }

    fun findVoteOptionsByVoteDate(allVoteOptions: List<VoteOption>): VoteOptionsByVoteDate {
        validateVoteOptionsSize(allVoteOptions)
        val voteOptionIndex: Int = (voteNumber * NUMBER_OF_VOTE_OPTIONS) % allVoteOptions.size
        validateVoteOptionsSizeMultipleOf5(allVoteOptions.size, voteOptionIndex)
        val voteOptionsByVoteDate = allVoteOptions.subList(voteOptionIndex, voteOptionIndex + NUMBER_OF_VOTE_OPTIONS)
        return VoteOptionsByVoteDate.of(date, voteOptionsByVoteDate)
    }

    private fun validateVoteOptionsSizeMultipleOf5(
        allVoteOptionsSize: Int,
        voteOptionIndex: Int
    ) {
        if (allVoteOptionsSize <= voteOptionIndex + NUMBER_OF_VOTE_OPTIONS) {
            throw IllegalArgumentException("선택지의 개수가 5의 배수가 아닙니다.")
        }
    }

    private fun validateVoteOptionsSize(allVoteOptions: List<VoteOption>) {
        if (allVoteOptions.size < 5) {
            throw IllegalArgumentException("선택지는 최소 5개 이상이어야 합니다.")
        }
    }

    fun findUsersForVote(classmates: List<User>, user: User): List<User> {
        val alreadyVotedByUser: List<Long> = ballots.findUserIdsVotedByUser(user.id)
        return classmates.stream()
            .filter { !alreadyVotedByUser.contains(it.id) && isNotMe(it, user) }
            .toList()
            .take(NUMBER_OF_VOTE_USERS)
    }

    private fun isNotMe(classmate: User, user: User) = classmate != user

    fun addBallot(
        voteOptionsByVoteDate: VoteOptionsByVoteDate,
        voteOptionId: Long,
        senderId: Long,
        receiverId: Long
    ) {
        voteOptionsByVoteDate.validateVoteOption(voteOptionId)
        ballots.add(
            Ballot.of(
                voteId = id,
                voteOptionId = voteOptionId,
                senderId = senderId,
                receiverId = receiverId
            )
        )
    }

    fun getBallots(): List<Ballot> {
        return ballots.ballots
            .map { it.value }
            .flatMap { it.values }
            .toList()
    }

    fun getRankedVoteResults(
        voteOptionsByVoteDate: VoteOptionsByVoteDate,
        users: List<User>
    ): Map<VoteOption, List<VoteRecord>> {
        val usersAssociateBy = users.associateBy { it.id }
        val rankedVoteResults: Map<Long, List<VoteRecord>> =
            getBallots().groupBy { it.voteOptionId }
                .mapValues { BallotsAggregator.of(it.key, it.value) }
                .mapValues { entry ->
                    entry.value.getRankResults()
                        .filter { usersAssociateBy.containsKey(it.userId) }
                        .map { VoteRecord.of(usersAssociateBy[it.userId]!!, it) }
                }
                .toMap(LinkedHashMap())

        return voteOptionsByVoteDate.voteOptions
            .associateWith { rankedVoteResults[it.id] ?: emptyList() }
            .toMap(LinkedHashMap())
    }

}

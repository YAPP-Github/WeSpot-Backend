package com.wespot.vote

import com.wespot.user.User
import com.wespot.voteoption.VoteOption
import java.time.LocalDateTime

data class Vote(
    private val id: Long,
    private val schoolName: String,
    private val grade: Int,
    private val groupNumber: Int,
    private val voteNumber: Int,
    private val date: LocalDateTime,
    private val ballots: Ballots
) {

    companion object {
        private val NUMBER_OF_VOTE_USERS = 5
        private val NUMBER_OF_VOTE_OPTIONS = 5
    }

    fun findTodayVoteOptions(voteOptions: List<VoteOption>): List<VoteOption> {
        if (voteOptions.size < 5) {
            throw IllegalArgumentException("선택지는 최소 5개 이상이어야 합니다.")
        }

        val todayVoteOptions: MutableList<VoteOption> = mutableListOf()
        var index: Int = (voteNumber * NUMBER_OF_VOTE_OPTIONS) % voteOptions.size
        while (todayVoteOptions.size < NUMBER_OF_VOTE_OPTIONS) {
            todayVoteOptions.add(voteOptions[index])
            index = (index + 1) % voteOptions.size
        }

        return todayVoteOptions
    }

    fun findVotedUsers(user: User): List<User> {
        val findUsersVotedByUser = ballots.findUsersVotedByUser(user)

        return findUsersVotedByUser.shuffled()
            .take(NUMBER_OF_VOTE_USERS)
    }

}
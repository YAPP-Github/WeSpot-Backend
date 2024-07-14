package com.wespot.vote

import com.wespot.user.User
import com.wespot.voteoption.VoteOption
import java.time.LocalDate
import java.time.LocalDateTime

data class Vote(
    val id: Long,
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
        if (voteOptions.size < 5) {
            throw IllegalArgumentException("선택지는 최소 5개 이상이어야 합니다.")
        }

        val todayVoteOptions: MutableList<VoteOption> = mutableListOf()
        var index: Int = (voteNumber * NUMBER_OF_VOTE_OPTIONS) % voteOptions.size
        while (todayVoteOptions.size < NUMBER_OF_VOTE_OPTIONS) {
            todayVoteOptions.add(voteOptions[index])
            index = (index + MOVE_TO_NEXT_VOTE_OPTION) % voteOptions.size
        }

        return todayVoteOptions
    }

    fun findVotedUsers(classmates: List<User>, user: User): List<User> {
        val alreadyVotedByUser: List<Long> = ballots.findUserIdsVotedByUser(user.id)
        return classmates.stream()
            .filter { classmate ->
                alreadyVotedByUser.contains(classmate.id) || isMe(
                    classmate,
                    user
                )
            }
            .toList()
            .shuffled()
            .take(NUMBER_OF_VOTE_USERS)
    }

    private fun isMe(classmate: User, user: User) = classmate == user

    fun addBallot(voteOptionId: Long, senderId: Long, receiverId: Long) {
        ballots.add(
            Ballot.of(
                id,
                voteOptionId,
                senderId,
                receiverId
            )
        )
    }

    fun getBallots(): List<Ballot> {
        return ballots.ballots
    }

}
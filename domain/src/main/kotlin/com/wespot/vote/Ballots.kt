package com.wespot.vote

import java.util.*

data class Ballots(
    val ballots: MutableList<Ballot>,
) {

    companion object {
        fun from(ballots: List<Ballot>): Ballots {
            return Ballots(ballots.toMutableList())
        }
    }

    fun add(ballot: Ballot) {
        validateNull(ballot)
        validateDuplicateVote(ballot)
        ballots.add(ballot)
    }

    private fun validateNull(ballot: Ballot) {
        if (Objects.isNull(ballot)) {
            throw IllegalArgumentException("빈 투표지를 제출할 수 없습니다.")
        }
    }

    private fun validateDuplicateVote(ballot: Ballot) {
        val isDuplicateBallot: Boolean = ballots.stream()
            .anyMatch { it -> isVoteSameClassmate(it, ballot) }
        if (isDuplicateBallot) {
            throw IllegalArgumentException("하루에 한 명의 회원에게 한 개의 투표만 할 수 있습니다.")
        }
    }

    private fun isVoteSameClassmate(it: Ballot, ballot: Ballot) =
        it.senderId == ballot.senderId && it.receiverId == ballot.receiverId

    fun findUserIdsVotedByUser(sentUserId: Long): List<Long> {
        return ballots.stream()
            .filter { ballot -> ballot.senderId == sentUserId }
            .map(Ballot::receiverId)
            .distinct()
            .toList()
    }

}
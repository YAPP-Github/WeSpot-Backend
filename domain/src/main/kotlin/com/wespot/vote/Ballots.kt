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
        if (Objects.isNull(ballot)) {
            throw IllegalArgumentException("빈 투표지를 제출할 수 없습니다.")
        }
        ballots.add(ballot)
    }

    fun findUserIdsVotedByUser(sentUserId: Long): List<Long> {
        return ballots.stream()
            .filter { ballot -> ballot.senderId == sentUserId }
            .map(Ballot::receiverId)
            .distinct()
            .toList()
    }

}
package com.wespot.vote

import com.wespot.user.User
import java.util.*

data class Ballots(
    private val ballots: MutableList<Ballot>,
) {

    fun add(ballot: Ballot) {
        if (Objects.isNull(ballot)) {
            throw IllegalArgumentException("빈 투표지를 제출할 수 없습니다.")
        }
        ballots.add(ballot)
    }

    fun findUsersVotedByUser(sentUser: User): List<User> {
        return ballots.stream()
            .filter { ballot -> ballot.sender == sentUser }
            .map(Ballot::receiver)
            .distinct()
            .toList()
    }

}
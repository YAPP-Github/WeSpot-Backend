package com.wespot.vote

import com.wespot.user.User

data class Ballots(
    private val ballots: MutableList<Ballot>,
) {

    fun addBallot(ballot: Ballot) {
        ballots.add(ballot);
    }

    fun findUsersVotedByUser(sentUser: User): List<User> {
        return ballots.stream()
            .filter { ballot -> ballot.sender == sentUser }
            .map(Ballot::receiver)
            .distinct()
            .toList()
    }

}
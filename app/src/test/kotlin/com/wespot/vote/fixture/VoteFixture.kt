package com.wespot.vote.fixture

import com.wespot.vote.Ballot
import com.wespot.vote.Ballots
import com.wespot.vote.Vote
import java.time.LocalDate
import java.util.*

object VoteFixture {

    fun create() = Vote(
        id = 1L,
        schoolId = 1L,
        grade = 1,
        groupNumber = 1,
        voteNumber = 0,
        date = LocalDate.now(),
        ballots = Ballots.from(Collections.emptyList())
    )

    fun createWithVoteNumberAndBallots(voteNumber: Int, ballots: List<Ballot>) = Vote(
        id = 1L,
        schoolId = 1L,
        grade = 1,
        groupNumber = 1,
        voteNumber = voteNumber,
        date = LocalDate.now(),
        ballots = Ballots.from(ballots)
    )

    fun createWithIdAndVoteNumberAndBallots(id: Long?, voteNumber: Int, ballots: List<Ballot>) =
        Vote(
            id = id,
            schoolId = 1L,
            grade = 1,
            groupNumber = 1,
            voteNumber = voteNumber,
            date = LocalDate.now(),
            ballots = Ballots.from(ballots)
        )

}

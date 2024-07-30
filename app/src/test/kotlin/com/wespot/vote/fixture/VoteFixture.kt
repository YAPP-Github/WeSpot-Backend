package com.wespot.vote.fixture

import com.wespot.vote.Ballot
import com.wespot.vote.Ballots
import com.wespot.vote.Vote
import com.wespot.vote.VoteIdentifier
import java.time.LocalDate
import java.util.*

object VoteFixture {

    fun create() = Vote(
        id = 1L,
        voteIdentifier = VoteIdentifier(1L, 1, 1,LocalDate.now()),
        voteNumber = 0,
        voteOptionsByVoteDate = VoteOptionsByVoteDateFixture.create(),
        ballots = Ballots.from(Collections.emptyList())
    )

    fun createWithVoteNumberAndBallots(voteNumber: Int, ballots: List<Ballot>) = Vote(
        id = 1L,
        voteIdentifier = VoteIdentifier(1L, 1, 1,LocalDate.now()),
        voteNumber = voteNumber,
        voteOptionsByVoteDate = VoteOptionsByVoteDateFixture.create(),
        ballots = Ballots.from(ballots)
    )

    fun createWithIdAndVoteNumberAndBallots(id: Long, voteNumber: Int, ballots: List<Ballot>) =
        Vote(
            id = id,
            voteIdentifier = VoteIdentifier(1L, 1, 1,LocalDate.now()),
            voteNumber = voteNumber,
            voteOptionsByVoteDate = VoteOptionsByVoteDateFixture.create(),
            ballots = Ballots.from(ballots)
        )

    fun createWithIdAndVoteNumberAndBallotsAndCreatedAt(
        id: Long,
        voteNumber: Int,
        ballots: List<Ballot>,
        createdAt: LocalDate
    ) =
        Vote(
            id = id,
            voteIdentifier = VoteIdentifier(1L, 1, 1,createdAt),
            voteNumber = voteNumber,
            voteOptionsByVoteDate = VoteOptionsByVoteDateFixture.create(),
            ballots = Ballots.from(ballots)
        )

}

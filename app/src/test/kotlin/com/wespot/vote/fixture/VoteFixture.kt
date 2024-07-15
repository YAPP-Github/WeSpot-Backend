package com.wespot.vote.fixture

import com.wespot.vote.Ballots
import com.wespot.vote.Vote
import java.time.LocalDate
import java.util.*

object VoteFixture {

    fun createMock() = Vote(
        id = 1L,
        schoolId = 1L,
        grade = 1,
        groupNumber = 1,
        voteNumber = 0,
        date = LocalDate.now(),
        ballots = Ballots.from(Collections.emptyList())
    )

}
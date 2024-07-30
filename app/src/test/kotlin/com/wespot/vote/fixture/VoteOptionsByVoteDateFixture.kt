package com.wespot.vote.fixture

import com.wespot.vote.VoteOptionsByVoteDate
import java.time.LocalDate

object VoteOptionsByVoteDateFixture {

    fun create() = VoteOptionsByVoteDate(
        voteDate = LocalDate.now(),
        voteOptionsByVoteDate = listOf(
            VoteOptionByVoteDateFixture.create(),
            VoteOptionByVoteDateFixture.create(),
            VoteOptionByVoteDateFixture.create(),
            VoteOptionByVoteDateFixture.create(),
            VoteOptionByVoteDateFixture.create()
        )
    )
}

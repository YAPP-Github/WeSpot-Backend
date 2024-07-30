package com.wespot.vote.fixture

import com.wespot.vote.VoteOptionByVoteDate
import com.wespot.voteoption.fixture.VoteOptionFixture

object VoteOptionByVoteDateFixture {

    fun create() = VoteOptionByVoteDate(
        id = 0L,
        voteId = 1L,
        voteOption = VoteOptionFixture.create(),
    )

    fun createWithVoteId(voteId: Long) = VoteOptionByVoteDate(
        id = 0L,
        voteId = voteId,
        voteOption = VoteOptionFixture.create(),
    )

}

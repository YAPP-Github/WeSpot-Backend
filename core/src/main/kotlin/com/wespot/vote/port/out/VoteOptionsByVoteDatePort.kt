package com.wespot.vote.port.out

import com.wespot.vote.VoteOptionsByVoteDate
import java.time.LocalDate

interface VoteOptionsByVoteDatePort {

    fun saveAll(voteOptionsByVoteDate: VoteOptionsByVoteDate): VoteOptionsByVoteDate

    fun findAllByVoteId(date: LocalDate, voteId: Long): VoteOptionsByVoteDate

}

package com.wespot.vote.port.out

import com.wespot.voteoption.VoteOption

interface VoteOptionPort {

    fun findById(id: Long): VoteOption?

    fun findAllVoteOption(): List<VoteOption>

}

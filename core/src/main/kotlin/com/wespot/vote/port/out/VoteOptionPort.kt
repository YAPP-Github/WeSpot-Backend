package com.wespot.vote.port.out

import com.wespot.voteoption.VoteOption

interface VoteOptionPort {

    fun findAllVoteOption(): List<VoteOption>

}
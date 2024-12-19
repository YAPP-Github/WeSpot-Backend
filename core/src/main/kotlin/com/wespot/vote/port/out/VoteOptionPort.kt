package com.wespot.vote.port.out

import com.wespot.voteoption.VoteOption

interface VoteOptionPort {

    fun save(voteOption: VoteOption): VoteOption

    fun findById(id: Long): VoteOption?

    fun findAll(): List<VoteOption>

    fun saveAll(voteOptions: List<VoteOption>): List<VoteOption>

}

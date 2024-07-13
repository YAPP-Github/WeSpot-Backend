package com.wespot.voteoption

import com.wespot.vote.VoteJpaRepository
import com.wespot.vote.port.out.VoteOptionPort
import org.springframework.stereotype.Repository

@Repository
class VoteOptionPersistenceAdapter(
    private val voteOptionJpaRepository: VoteJpaRepository
) : VoteOptionPort {
}
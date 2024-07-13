package com.wespot.vote

import com.wespot.vote.port.out.VotePort
import org.springframework.stereotype.Repository

@Repository
class VotePersistenceAdapter(
    private val voteJpaRepository: VoteJpaRepository
) : VotePort {
}
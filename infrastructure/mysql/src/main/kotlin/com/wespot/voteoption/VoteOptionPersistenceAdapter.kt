package com.wespot.voteoption

import com.wespot.vote.port.out.VoteOptionPort
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class VoteOptionPersistenceAdapter(
    private val voteOptionJpaRepository: VoteOptionJpaRepository
) : VoteOptionPort {

    override fun findById(id: Long): VoteOption? {
        return voteOptionJpaRepository.findById(id)
            .getOrNull()
            ?.let { voteOptionJpaEntity -> VoteOptionMapper.mapToDomainEntity(voteOptionJpaEntity) }
    }

    override fun findAllVoteOption(): List<VoteOption> {
        return voteOptionJpaRepository.findAll()
            .stream()
            .map { voteOptionJpaEntity -> VoteOptionMapper.mapToDomainEntity(voteOptionJpaEntity) }
            .toList()
    }

}
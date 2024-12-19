package com.wespot.voteoption

import com.wespot.vote.port.out.VoteOptionPort
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class VoteOptionPersistenceAdapter(
    private val voteOptionJpaRepository: VoteOptionJpaRepository
) : VoteOptionPort {

    override fun save(voteOption: VoteOption): VoteOption {
        val voteOptionJpaEntity = VoteOptionMapper.mapToJpaEntity(voteOption)

        return VoteOptionMapper.mapToDomainEntity(voteOptionJpaRepository.save(voteOptionJpaEntity))
    }

    override fun findById(id: Long): VoteOption? {
        return voteOptionJpaRepository.findById(id)
            .getOrNull()
            ?.let { voteOptionJpaEntity -> VoteOptionMapper.mapToDomainEntity(voteOptionJpaEntity) }
    }

    override fun findAll(): List<VoteOption> {
        return voteOptionJpaRepository.findAll()
            .stream()
            .map { voteOptionJpaEntity -> VoteOptionMapper.mapToDomainEntity(voteOptionJpaEntity) }
            .toList()
    }

    override fun saveAll(voteOptions: List<VoteOption>): List<VoteOption> {
        val voteOptionJpaEntities = voteOptions.map { VoteOptionMapper.mapToJpaEntity(it) }

        return voteOptionJpaRepository.saveAll(voteOptionJpaEntities)
            .map { voteOptionJpaEntity -> VoteOptionMapper.mapToDomainEntity(voteOptionJpaEntity) }
    }

}

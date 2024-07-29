package com.wespot.vote

import com.wespot.vote.port.out.VoteOptionsByVoteDatePort
import com.wespot.voteoption.VoteOption
import com.wespot.voteoption.VoteOptionJpaRepository
import com.wespot.voteoption.VoteOptionMapper
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class VoteOptionsByVoteDatePersistenceAdapter(
    private val voteOptionByVoteDateJpaRepository: VoteOptionByVoteDateJpaRepository,
    private val voteOptionJpaRepository: VoteOptionJpaRepository
) : VoteOptionsByVoteDatePort {

    override fun saveAll(voteOptionsByVoteDate: VoteOptionsByVoteDate): VoteOptionsByVoteDate {
        val response = voteOptionsByVoteDate.voteOptionsByVoteDate
            .map { VoteOptionByVoteDateMapper.mapToJpaEntity(it) }
            .map { voteOptionByVoteDateJpaRepository.save(it) }
            .map {
                VoteOptionByVoteDate(
                    id = it.id,
                    voteId = it.voteId,
                    voteOption = getVoteOptionByVoteOptionsByVoteDate(voteOptionsByVoteDate, it.voteOptionId)
                )
            }
            .toList()

        return VoteOptionsByVoteDate(voteOptionsByVoteDate.voteDate, response)
    }

    private fun getVoteOptionByVoteOptionsByVoteDate(
        voteOptionsByVoteDate: VoteOptionsByVoteDate,
        voteOptionId: Long
    ): VoteOption {
        return voteOptionsByVoteDate.voteOptionsByVoteDate
            .map { it.voteOption }
            .find { it.id == voteOptionId }!!
    }

    override fun findAllByVoteId(date: LocalDate, voteId: Long): VoteOptionsByVoteDate {
        val response = voteOptionByVoteDateJpaRepository.findAllByVoteId(voteId)
            .map { VoteOptionByVoteDateMapper.mapToDomainEntity(it, getVoteOption(it.voteOptionId)) }
            .toList()

        return VoteOptionsByVoteDate(date, response)
    }

    private fun getVoteOption(voteOptionId: Long): VoteOption {
        return voteOptionJpaRepository.findByIdOrNull(voteOptionId)
            ?.let { VoteOptionMapper.mapToDomainEntity(it) }
            ?: throw IllegalArgumentException("해당하는 ID의 질문지가 존재하지 않습니다.")
    }

}

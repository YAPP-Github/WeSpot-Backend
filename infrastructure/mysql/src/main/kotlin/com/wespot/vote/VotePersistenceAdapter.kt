package com.wespot.vote

import com.wespot.vote.port.out.VotePort
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class VotePersistenceAdapter(
    private val voteJpaRepository: VoteJpaRepository,
    private val ballotJpaRepository: BallotJpaRepository
) : VotePort {

    override fun findBySchoolIdAndGradeAndClassNumberAndDate(
        schoolId: Long,
        grade: Int,
        classNumber: Int,
        date: LocalDate
    ): Vote? {
        return voteJpaRepository.findBySchoolIdAndGradeAndClassNumberAndDate(
            schoolId,
            grade,
            classNumber,
            date
        )?.let { voteJpaEntity ->
            VoteMapper.mapToDomainEntity(
                voteJpaEntity,
                ballotJpaRepository.findAllByVoteId(voteJpaEntity.id)
                    .stream()
                    .map { ballot -> BallotMapper.mapToDomainEntity(ballot) }
                    .toList()
            )
        }
    }

    override fun save(vote: Vote): Vote {
        vote.getBallots()
            .stream()
            .map { ballot -> BallotMapper.mapToJpaEntity(ballot) }
            .forEach { ballotJpaEntity -> ballotJpaRepository.save(ballotJpaEntity) }
        voteJpaRepository.save(VoteMapper.mapToJpaEntity(vote))
        return vote
    }

}

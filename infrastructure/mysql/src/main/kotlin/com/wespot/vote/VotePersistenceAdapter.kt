package com.wespot.vote

import com.wespot.vote.port.out.VotePort
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
class VotePersistenceAdapter(
    private val voteJpaRepository: VoteJpaRepository,
    private val ballotJpaRepository: BallotJpaRepository
) : VotePort {

    override fun findTop1BySchoolIdAndGradeAndClassNumberOrderByDateDescExcludeBallots(
        schoolId: Long,
        grade: Int,
        classNumber: Int
    ): Vote? {
        return voteJpaRepository.findTop1BySchoolIdAndGradeAndClassNumberOrderByDateDesc(
            schoolId,
            grade,
            classNumber
        )?.let { VoteMapper.mapToDomainEntity(it, Collections.emptyList()) } ?: return null
    }

    override fun existsBySchoolIdAndGradeAndClassNumberAndDate(
        schoolId: Long,
        grade: Int,
        classNumber: Int,
        date: LocalDate
    ): Boolean {
        return voteJpaRepository.existsBySchoolIdAndGradeAndClassNumberAndDate(
            schoolId,
            grade,
            classNumber,
            date
        )
    }

    override fun existsById(id: Long): Boolean {
        return voteJpaRepository.existsById(id)
    }

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
                findBallotsByVote(voteJpaEntity.id)
            )
        }
    }

    private fun findBallotsByVote(voteId: Long): List<Ballot> =
        ballotJpaRepository.findAllByVoteId(voteId)
            .stream()
            .map { BallotMapper.mapToDomainEntity(it) }
            .toList()

    override fun save(vote: Vote): Vote {
        vote.getBallots()
            .stream()
            .map { ballot -> BallotMapper.mapToJpaEntity(ballot) }
            .forEach { ballotJpaEntity -> ballotJpaRepository.save(ballotJpaEntity) }
        voteJpaRepository.save(VoteMapper.mapToJpaEntity(vote))
        return vote
    }

    override fun findAllBySchoolIdAndGradeAndClassNumberByOrderByDateDesc(
        schoolId: Long,
        grade: Int,
        classNumber: Int
    ): List<Vote> {
        return voteJpaRepository.findAllBySchoolIdAndGradeAndClassNumberOrderByDateDesc(schoolId, grade, classNumber)
            .stream()
            .map {
                VoteMapper.mapToDomainEntity(
                    it,
                    findBallotsByVote(it.id)
                )
            }
            .toList()
    }

}

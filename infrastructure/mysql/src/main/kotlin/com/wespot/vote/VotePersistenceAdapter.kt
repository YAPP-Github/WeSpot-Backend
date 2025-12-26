package com.wespot.vote

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.vote.port.out.VotePort
import com.wespot.voteoption.VoteOption
import com.wespot.voteoption.VoteOptionJpaRepository
import com.wespot.voteoption.VoteOptionMapper
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class VotePersistenceAdapter(
    private val voteJpaRepository: VoteJpaRepository,
    private val voteOptionByVoteDateJpaRepository: VoteOptionByVoteDateJpaRepository,
    private val voteOptionJpaRepository: VoteOptionJpaRepository,
    private val ballotJpaRepository: BallotJpaRepository
) : VotePort {

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
                findAllByVoteId(date, voteJpaEntity.id),
                findBallotsByVote(voteJpaEntity.id)
            )
        }
    }

    private fun findAllByVoteId(date: LocalDate, voteId: Long): VoteOptionsByVoteDate {
        val response = voteOptionByVoteDateJpaRepository.findAllByVoteId(voteId)
            .map { VoteOptionByVoteDateMapper.mapToDomainEntity(it, getVoteOption(it.voteOptionId)) }
            .toList()

        return VoteOptionsByVoteDate(date, response)
    }

    private fun getVoteOption(voteOptionId: Long): VoteOption {
        return voteOptionJpaRepository.findByIdOrNull(voteOptionId)
            ?.let { VoteOptionMapper.mapToDomainEntity(it) }
            ?: throw CustomException(HttpStatus.NOT_FOUND, ExceptionView.TOAST, "해당하는 ID의 질문지가 존재하지 않습니다.")
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
        val savedVote = voteJpaRepository.save(VoteMapper.mapToJpaEntity(vote))
        vote.voteOptionsByVoteDate.voteOptionsByVoteDate
            .stream()
            .map { VoteOptionByVoteDateMapper.mapToJpaEntity(savedVote.id, it) }
            .forEach { voteOptionByVoteDateJpaRepository.save(it) }
        return VoteMapper.mapToDomainEntity(savedVote, vote.voteOptionsByVoteDate, vote.getBallots())
    }

    override fun findAllBySchoolIdAndGradeAndClassNumberAndReceiverIdOrderByDateDesc(
        schoolId: Long,
        grade: Int,
        classNumber: Int,
        receiverId: Long,
        cursorId: Long,
        limit: Long
    ): List<Vote> {
        return voteJpaRepository.findAllBySchoolIdAndGradeAndClassNumberAndReceiverIdOrderByDateDesc(
            schoolId,
            grade,
            classNumber,
            receiverId,
            cursorId,
            limit
        ).map {
            VoteMapper.mapToDomainEntity(
                it,
                findAllByVoteId(it.date, it.id),
                findBallotsByVote(it.id)
            )
        }.toList()
    }

    override fun findAllBySchoolIdAndGradeAndClassNumberAndSenderIdOrderByDateDesc(
        schoolId: Long,
        grade: Int,
        classNumber: Int,
        senderId: Long,
        cursorId: Long,
        limit: Long
    ): List<Vote> {
        return voteJpaRepository.findAllBySchoolIdAndGradeAndClassNumberAndSenderIdOrderByDateDesc(
            schoolId,
            grade,
            classNumber,
            senderId,
            cursorId,
            limit
        ).map {
            VoteMapper.mapToDomainEntity(
                it,
                findAllByVoteId(it.date, it.id),
                findBallotsByVote(it.id)
            )
        }.toList()
    }

}

package com.wespot.vote.service

import com.wespot.user.User
import com.wespot.user.port.out.UserPort
import com.wespot.vote.Vote
import com.wespot.vote.VoteIdentifier
import com.wespot.vote.port.`in`.CreatedVoteUseCase
import com.wespot.vote.port.out.VoteOptionPort
import com.wespot.vote.port.out.VotePort
import com.wespot.vote.service.helper.VoteServiceHelper
import com.wespot.voteoption.VoteOption
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class CreatedVoteService(
    private val votePort: VotePort,
    private val userPort: UserPort,
    private val voteOptionPort: VoteOptionPort,
) : CreatedVoteUseCase {

    @Transactional
    override fun createVotes() {
        val today = LocalDate.now()
        val allVoteOptions = voteOptionPort.findAll()

        userPort.findAll()
            .map { VoteIdentifier.of(it, today) }
            .distinct()
            .forEach { saveVote(it, allVoteOptions) }
    }

    private fun saveVote(voteIdentifier: VoteIdentifier, allVoteOptions: List<VoteOption>) {
        if (isAlreadyExistsVote(voteIdentifier)) {
            return
        }

        val vote = Vote.of(voteIdentifier, allVoteOptions, getPreviousVoteNumber(voteIdentifier))
        votePort.save(vote)
    }

    private fun isAlreadyExistsVote(voteIdentifier: VoteIdentifier): Boolean {
        return votePort.existsBySchoolIdAndGradeAndClassNumberAndDate(
            voteIdentifier.schoolId,
            voteIdentifier.grade,
            voteIdentifier.classNumber,
            voteIdentifier.date
        )
    }

    private fun getPreviousVoteNumber(voteIdentifier: VoteIdentifier): Vote? {
        val yesterday = voteIdentifier.date
            .minusDays(1)

        return votePort.findBySchoolIdAndGradeAndClassNumberAndDate(
            voteIdentifier.schoolId,
            voteIdentifier.grade,
            voteIdentifier.classNumber,
            yesterday
        )
    }

    @Transactional
    override fun createVoteByUser(user: User) {
        VoteServiceHelper.findUser(userPort, user.id)
        val allVoteOptions = voteOptionPort.findAll()
        val today = LocalDate.now()
        val voteIdentifier = VoteIdentifier.of(user, today)
        saveVote(voteIdentifier, allVoteOptions)
    }

}

package com.wespot.vote.service

import com.wespot.user.User
import com.wespot.user.port.out.UserPort
import com.wespot.vote.Vote
import com.wespot.vote.VoteIdentifier
import com.wespot.vote.port.`in`.CreatedVoteUseCase
import com.wespot.vote.port.out.VotePort
import com.wespot.vote.service.helper.VoteServiceHelper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class CreatedVoteService(
    private val votePort: VotePort,
    private val userPort: UserPort,
) : CreatedVoteUseCase {

    @Transactional
    override fun createVotes() {
        val today = LocalDate.now()

        userPort.findAll()
            .map { VoteIdentifier.of(it, today) }
            .distinct()
            .forEach { saveVote(it) }
    }

    private fun saveVote(voteIdentifier: VoteIdentifier) {
        if (isAlreadyExistsVote(voteIdentifier)) {
            return
        }

        val vote = Vote.of(voteIdentifier, getPreviousVoteNumber(voteIdentifier))
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
        val today = LocalDate.now()
        val voteIdentifier = VoteIdentifier.of(user, today)
        saveVote(voteIdentifier)
    }

}

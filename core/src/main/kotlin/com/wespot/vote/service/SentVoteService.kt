package com.wespot.vote.service

import com.wespot.CursorUtils
import com.wespot.user.User
import com.wespot.user.port.out.UserPort
import com.wespot.vote.CompleteBallot
import com.wespot.vote.Vote
import com.wespot.vote.dto.response.sent.SentVotesResponses
import com.wespot.vote.port.`in`.SentVoteUseCase
import com.wespot.vote.port.out.VotePort
import com.wespot.vote.service.helper.VoteServiceHelper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SentVoteService(
    private val votePort: VotePort,
    private val userPort: UserPort,
) : SentVoteUseCase {

    @Transactional(readOnly = true)
    override fun getSentVotes(cursorId: Long?, limit: Long): SentVotesResponses {
        val user = VoteServiceHelper.findLoginUser(userPort)
        val votes = VoteServiceHelper.findVotesSenderIdOrderByDateDesc(
            votePort = votePort,
            user = user,
            cursorId = CursorUtils.getEffectiveCursorId(cursorId),
            limit = limit + 1
        )
        val classmates = VoteServiceHelper.findClassmatesByUser(userPort, user)
        val voteResults = votes.take(limit.toInt()).associateWith { getSentVotes(it, user, classmates) }

        return SentVotesResponses.from(voteResults, votes.size.toLong() == limit + 1)
    }

    private fun getSentVotes(
        vote: Vote,
        user: User,
        classmates: List<User>
    ): List<CompleteBallot> {
        return vote.getUserSentVotes(user = user, classmates = classmates)
    }

}

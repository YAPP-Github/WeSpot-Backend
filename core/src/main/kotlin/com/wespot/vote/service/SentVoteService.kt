package com.wespot.vote.service

import com.wespot.user.User
import com.wespot.user.port.out.UserPort
import com.wespot.vote.CompleteBallot
import com.wespot.vote.Vote
import com.wespot.vote.dto.response.sent.SentVotesResponses
import com.wespot.vote.port.`in`.SentVoteUseCase
import com.wespot.vote.port.out.VotePort
import com.wespot.vote.service.helper.VoteServiceHelper
import org.springframework.stereotype.Service

@Service
class SentVoteService(
    private val votePort: VotePort,
    private val userPort: UserPort,
) : SentVoteUseCase {

    override fun getSentVotes(): SentVotesResponses {
        val user = VoteServiceHelper.findLoginUser(userPort)
        val votes = VoteServiceHelper.findVotesOrderByDateDesc(votePort, user)
        val classmates = VoteServiceHelper.findClassmatesByUser(userPort, user)
        val voteResults = votes.associateWith { getSentVotes(it, user, classmates) }

        return SentVotesResponses.from(voteResults)
    }

    private fun getSentVotes(
        vote: Vote,
        user: User,
        classmates: List<User>
    ): List<CompleteBallot> {
        return vote.getUserSentVotes(user = user, classmates = classmates)
    }

}

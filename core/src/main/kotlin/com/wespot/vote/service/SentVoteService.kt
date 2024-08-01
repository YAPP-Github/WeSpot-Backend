package com.wespot.vote.service

import com.wespot.user.User
import com.wespot.user.port.out.UserPort
import com.wespot.vote.Ballot
import com.wespot.vote.Vote
import com.wespot.vote.dto.response.sent.SentVoteResponse
import com.wespot.vote.dto.response.sent.SentVotesResponses
import com.wespot.vote.port.`in`.SentVoteUseCase
import com.wespot.vote.port.out.VoteOptionPort
import com.wespot.vote.port.out.VotePort
import com.wespot.vote.service.helper.VoteServiceHelper
import com.wespot.voteoption.VoteOption
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class SentVoteService(
    private val votePort: VotePort,
    private val userPort: UserPort,
    private val voteOptionPort: VoteOptionPort
) : SentVoteUseCase {

    override fun getSentVotes(): SentVotesResponses {
        val userId = VoteServiceHelper.findLoginUserId(userPort)
        val user = VoteServiceHelper.findUser(userPort, userId)
        val votes = VoteServiceHelper.findVotesOrderByDateDesc(votePort, user)
        val voteResults = votes.associateWith { getSentVotes(it, user) }

        return SentVotesResponses.from(voteResults)
    }

    private fun getSentVotes(
        vote: Vote,
        user: User
    ): Map<VoteOption, List<Ballot>> {
        return vote.getUserSentVotes(user = user)
    }

    override fun getSentVote(optionId: Long, date: LocalDate): SentVoteResponse {
        val userId = VoteServiceHelper.findLoginUserId(userPort)
        val user = VoteServiceHelper.findUser(userPort, userId)
        val vote = VoteServiceHelper.findVoteByUser(votePort, user, date)
        val voteOption = VoteServiceHelper.findVoteOptionById(voteOptionPort, optionId)

        return SentVoteResponse.of(
            voteOption = voteOption,
            users = vote.getUserSentVote(voteOption, user)
                .map { VoteServiceHelper.findUser(userPort, it.receiverId) }
                .toList()
        )
    }

}

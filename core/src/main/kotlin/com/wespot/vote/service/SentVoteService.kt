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
    private val voteOptionPort: VoteOptionPort,
    private val userPort: UserPort,
) : SentVoteUseCase {

    override fun getSentVotes(): SentVotesResponses {
        val userId = VoteServiceHelper.findLoginUserId(userPort)
        val user = VoteServiceHelper.findUser(userPort, userId)
        val votes = VoteServiceHelper.findVotesOrderByDateDesc(votePort, user)
        val voteOptions = voteOptionPort.findAllVoteOption()
        val voteResults = votes.associateWith { getSentVotes(it, voteOptions, user) }

        return SentVotesResponses.from(voteResults)
    }

    private fun getSentVotes(
        vote: Vote,
        voteOptions: List<VoteOption>,
        user: User
    ): Map<VoteOption, List<Ballot>> {
        val voteOptionsByVoteDate = vote.findVoteOptionsByVoteDate(voteOptions)

        return vote.getUserSentVotes(voteOptionsByVoteDate, user)
    }

    override fun getSentVote(optionId: Long, date: LocalDate): SentVoteResponse {
        val userId = VoteServiceHelper.findLoginUserId(userPort)
        val user = VoteServiceHelper.findUser(userPort, userId)
        val vote = VoteServiceHelper.findVoteByUser(votePort, user, date)
        val voteOptions = voteOptionPort.findAllVoteOption()
        val voteOption = VoteServiceHelper.findVoteOptionOnAllVoteOptions(voteOptions, optionId)
        val voteOptionsByVoteDate = vote.findVoteOptionsByVoteDate(voteOptions)

        return SentVoteResponse.of(
            voteOption = voteOption,
            users = vote.getUserSentVote(voteOptionsByVoteDate, voteOption, user)
                .map { VoteServiceHelper.findUser(userPort, it.receiverId) }
                .toList()
        )
    }

}

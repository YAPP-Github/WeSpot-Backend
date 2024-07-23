package com.wespot.vote.service

import com.wespot.user.port.out.UserPort
import com.wespot.vote.dto.response.sent.SentVoteResponse
import com.wespot.vote.dto.response.sent.SentVotesResponses
import com.wespot.vote.port.`in`.SentVoteUseCase
import com.wespot.vote.port.out.VoteOptionPort
import com.wespot.vote.port.out.VotePort
import com.wespot.vote.service.helper.VoteServiceHelper
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

        return SentVotesResponses.from(
            votes.associateWith {
                it.getUserSentVotes(
                    it.findVoteOptionsByVoteDate(voteOptions),
                    user,
                )
            }
        )
    }

    override fun getSentVote(optionId: Long, date: LocalDate): SentVoteResponse {
        val userId = VoteServiceHelper.findLoginUserId(userPort)
        val user = VoteServiceHelper.findUser(userPort, userId)
        val vote = VoteServiceHelper.findVoteByUser(votePort, user, date)
        val voteOptions = voteOptionPort.findAllVoteOption()
        val voteOption = VoteServiceHelper.findVoteOptionOnAllVoteOptions(voteOptions, optionId)

        return SentVoteResponse.of(
            voteOption,
            vote.getUserSentVote(vote.findVoteOptionsByVoteDate(voteOptions), voteOption, user)
                .map { VoteServiceHelper.findUser(userPort, it.receiverId) }
                .toList()
        )
    }

}

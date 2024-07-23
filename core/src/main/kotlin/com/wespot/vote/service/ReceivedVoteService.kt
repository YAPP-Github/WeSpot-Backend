package com.wespot.vote.service

import com.wespot.user.port.out.UserPort
import com.wespot.vote.dto.response.received.ReceivedVoteResponse
import com.wespot.vote.dto.response.received.ReceivedVotesResponses
import com.wespot.vote.port.`in`.ReceivedVoteUseCase
import com.wespot.vote.port.out.VoteOptionPort
import com.wespot.vote.port.out.VotePort
import com.wespot.vote.service.helper.VoteServiceHelper
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ReceivedVoteService(
    private val votePort: VotePort,
    private val voteOptionPort: VoteOptionPort,
    private val userPort: UserPort,
) : ReceivedVoteUseCase {

    override fun getReceivedVotes(): ReceivedVotesResponses {
        val userId = VoteServiceHelper.findLoginUserId(userPort)
        val user = VoteServiceHelper.findUser(userPort, userId)
        val votes = VoteServiceHelper.findVotesOrderByDateDesc(votePort, user)
        val voteOptions = voteOptionPort.findAllVoteOption()

        return ReceivedVotesResponses.of(
            voteResults = votes.associateWith {
                it.getUserReceivedVotes(it.findVoteOptionsByVoteDate(voteOptions), user)
            }
        )
    }

    @Transactional
    override fun getReceivedVote(
        optionId: Long,
        date: LocalDate
    ): ReceivedVoteResponse {
        val userId = VoteServiceHelper.findLoginUserId(userPort)
        val user = VoteServiceHelper.findUser(userPort, userId)
        val vote = VoteServiceHelper.findVoteByUser(votePort, user, date)
        val voteOptions = voteOptionPort.findAllVoteOption()
        val voteOption = VoteServiceHelper.findVoteOptionOnAllVoteOptions(voteOptions, optionId)
        val voteRecord = vote.getUserReceivedVote(
            vote.findVoteOptionsByVoteDate(voteOptions),
            voteOption,
            user
        )
        votePort.save(vote)

        return ReceivedVoteResponse.of(
            rate = voteRecord.rate.value,
            voteOption = voteOption,
            voteRecord = voteRecord
        )
    }

}

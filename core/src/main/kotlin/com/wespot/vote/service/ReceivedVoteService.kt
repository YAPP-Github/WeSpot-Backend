package com.wespot.vote.service

import com.wespot.user.User
import com.wespot.user.port.out.UserPort
import com.wespot.vote.ReceivedVoteCalculateService
import com.wespot.vote.Vote
import com.wespot.vote.VoteRecord
import com.wespot.vote.dto.response.received.ReceivedVoteResponse
import com.wespot.vote.dto.response.received.ReceivedVotesResponses
import com.wespot.vote.port.`in`.ReceivedVoteUseCase
import com.wespot.vote.port.out.VoteOptionPort
import com.wespot.vote.port.out.VotePort
import com.wespot.vote.service.helper.VoteServiceHelper
import com.wespot.voteoption.VoteOption
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ReceivedVoteService(
    private val votePort: VotePort,
    private val userPort: UserPort,
    private val voteOptionPort: VoteOptionPort,
    private val receivedVoteCalculateService: ReceivedVoteCalculateService
) : ReceivedVoteUseCase {

    override fun getReceivedVotes(): ReceivedVotesResponses {
        val user = VoteServiceHelper.findLoginUser(userPort)
        val votes = VoteServiceHelper.findVotesOrderByDateDesc(votePort, user)
        val voteResults = votes.associateWith { getUserReceivedVotesByVote(it, user) }

        return ReceivedVotesResponses.of(voteResults = voteResults)
    }

    private fun getUserReceivedVotesByVote(
        vote: Vote,
        user: User
    ): Map<VoteOption, VoteRecord> {
        return vote.getUserReceivedVotes(
            user = user,
            receivedVoteCalculateService = receivedVoteCalculateService
        )
    }

    @Transactional
    override fun getReceivedVote(
        optionId: Long,
        date: LocalDate
    ): ReceivedVoteResponse {
        val user = VoteServiceHelper.findLoginUser(userPort)
        val vote = VoteServiceHelper.findVoteByUser(votePort, user, date)
        val voteOption = VoteServiceHelper.findVoteOptionById(voteOptionPort, optionId)
        val voteRecord = vote.getUserReceivedVote(
            voteOption = voteOption,
            user = user,
            receivedVoteCalculateService = receivedVoteCalculateService
        )
        votePort.save(vote)

        return ReceivedVoteResponse.of(
            rate = voteRecord.rate.value,
            voteOption = voteOption,
            voteRecord = voteRecord
        )
    }

}

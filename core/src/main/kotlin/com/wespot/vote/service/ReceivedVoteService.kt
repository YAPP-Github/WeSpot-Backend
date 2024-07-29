package com.wespot.vote.service

import com.wespot.user.User
import com.wespot.user.port.out.UserPort
import com.wespot.vote.ReceivedVoteCalculateService
import com.wespot.vote.Vote
import com.wespot.vote.VoteRecord
import com.wespot.vote.dto.response.received.ReceivedVoteResponse
import com.wespot.vote.dto.response.received.ReceivedVotesResponses
import com.wespot.vote.port.`in`.ReceivedVoteUseCase
import com.wespot.vote.port.out.VoteOptionsByVoteDatePort
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
    private val voteOptionsByVoteDatePort: VoteOptionsByVoteDatePort,
    private val receivedVoteCalculateService: ReceivedVoteCalculateService
) : ReceivedVoteUseCase {

    override fun getReceivedVotes(): ReceivedVotesResponses {
        val userId = VoteServiceHelper.findLoginUserId(userPort)
        val user = VoteServiceHelper.findUser(userPort, userId)
        val votes = VoteServiceHelper.findVotesOrderByDateDesc(votePort, user)
        val voteResults = votes.associateWith { getUserReceivedVotesByVote(it, user) }

        return ReceivedVotesResponses.of(voteResults = voteResults)
    }

    private fun getUserReceivedVotesByVote(
        vote: Vote,
        user: User
    ): Map<VoteOption, VoteRecord> {
        val voteOptionsByVoteDate = VoteServiceHelper.findVoteOptionsByVoteDate(voteOptionsByVoteDatePort, vote)

        return vote.getUserReceivedVotes(
            voteOptionsByVoteDate = voteOptionsByVoteDate,
            user = user,
            receivedVoteCalculateService = receivedVoteCalculateService
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
        val voteOptionsByVoteDate = VoteServiceHelper.findVoteOptionsByVoteDate(voteOptionsByVoteDatePort, vote)
        val voteOption = VoteServiceHelper.findVoteOptionOnVoteOptions(voteOptionsByVoteDate, optionId)
        val voteRecord = vote.getUserReceivedVote(
            voteOptionsByVoteDate = voteOptionsByVoteDate,
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

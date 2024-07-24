package com.wespot.vote.port.`in`

import com.wespot.vote.dto.response.received.ReceivedVoteResponse
import com.wespot.vote.dto.response.received.ReceivedVotesResponses
import java.time.LocalDate

interface ReceivedVoteUseCase {

    fun getReceivedVotes(): ReceivedVotesResponses

    fun getReceivedVote(optionId: Long, date: LocalDate): ReceivedVoteResponse

}

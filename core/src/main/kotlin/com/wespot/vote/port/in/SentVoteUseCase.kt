package com.wespot.vote.port.`in`

import com.wespot.vote.dto.response.sent.SentVoteResponse
import com.wespot.vote.dto.response.sent.SentVotesResponses
import java.time.LocalDate

interface SentVoteUseCase {

    fun getSentVotes(): SentVotesResponses

    fun getSentVote(optionId: Long, date: LocalDate): SentVoteResponse

}

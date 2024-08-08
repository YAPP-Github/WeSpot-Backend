package com.wespot.vote.port.`in`

import com.wespot.vote.dto.response.sent.SentVotesResponses

interface SentVoteUseCase {

    fun getSentVotes(cursorId: Long?, limit: Long): SentVotesResponses

}

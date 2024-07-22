package com.wespot.vote.port.`in`

import com.wespot.vote.dto.response.top1.VoteResultResponsesOfTop1
import com.wespot.vote.dto.response.top5.VoteResultResponsesOfTop5
import java.time.LocalDate

interface VoteRankUseCase {

    fun getVoteResultsOfTop5(date: LocalDate): VoteResultResponsesOfTop5

    fun getVoteResultsOfTop1(date: LocalDate): VoteResultResponsesOfTop1

}

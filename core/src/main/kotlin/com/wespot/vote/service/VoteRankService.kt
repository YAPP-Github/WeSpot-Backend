package com.wespot.vote.service

import com.wespot.user.port.out.UserPort
import com.wespot.vote.dto.response.top1.VoteResultResponsesOfTop1
import com.wespot.vote.dto.response.top5.VoteResultResponsesOfTop5
import com.wespot.vote.port.`in`.VoteRankUseCase
import com.wespot.vote.port.out.VoteOptionPort
import com.wespot.vote.port.out.VotePort
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class VoteRankService(
    private val votePort: VotePort,
    private val voteOptionPort: VoteOptionPort,
    private val userPort: UserPort,
) : VoteRankUseCase {

    override fun getVoteResultsOfTop5(userId: Long, date: LocalDate): VoteResultResponsesOfTop5 {
        TODO("Not yet implemented")
    }

    override fun getVoteResultsOfTop1(userId: Long, date: LocalDate): VoteResultResponsesOfTop1 {
        TODO("Not yet implemented")
    }

}

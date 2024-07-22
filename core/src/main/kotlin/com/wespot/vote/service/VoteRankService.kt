package com.wespot.vote.service

import com.wespot.user.port.out.UserPort
import com.wespot.vote.VoteRecord
import com.wespot.vote.dto.response.top1.VoteResultResponsesOfTop1
import com.wespot.vote.dto.response.top5.VoteResultResponsesOfTop5
import com.wespot.vote.port.`in`.VoteRankUseCase
import com.wespot.vote.port.out.VoteOptionPort
import com.wespot.vote.port.out.VotePort
import com.wespot.vote.service.helper.VoteServiceHelper
import com.wespot.voteoption.VoteOption
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class VoteRankService(
    private val votePort: VotePort,
    private val voteOptionPort: VoteOptionPort,
    private val userPort: UserPort,
) : VoteRankUseCase {

    override fun getVoteResultsOfTop5(userId: Long, date: LocalDate): VoteResultResponsesOfTop5 {
        return VoteResultResponsesOfTop5.from(getRankedVoteResults(userId, date))
    }

    private fun getRankedVoteResults(
        userId: Long,
        date: LocalDate
    ): Map<VoteOption, List<VoteRecord>> {
        val user = VoteServiceHelper.findUser(userPort, userId)
        val classmates = VoteServiceHelper.findClassmatesByUser(userPort, user)
        val vote = VoteServiceHelper.findVoteByUser(votePort, user, date)
        val voteOptions = VoteServiceHelper.findVoteOptionsByVoteDate(voteOptionPort, vote)
        val rankedVoteResults = vote.getRankedVoteResults(voteOptions, classmates)

        return rankedVoteResults
    }

    override fun getVoteResultsOfTop1(userId: Long, date: LocalDate): VoteResultResponsesOfTop1 {
        return VoteResultResponsesOfTop1.from(getRankedVoteResults(userId, date))
    }

}

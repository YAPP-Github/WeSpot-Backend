package com.wespot.vote.service

import com.wespot.user.port.out.UserPort
import com.wespot.vote.RankCalculateService
import com.wespot.vote.VoteRecord
import com.wespot.vote.dto.response.top1.VoteResultResponsesOfTop1
import com.wespot.vote.dto.response.top5.VoteResultResponsesOfTop5
import com.wespot.vote.port.`in`.VoteRankUseCase
import com.wespot.vote.port.out.VoteOptionsByVoteDatePort
import com.wespot.vote.port.out.VotePort
import com.wespot.vote.service.helper.VoteServiceHelper
import com.wespot.voteoption.VoteOption
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class VoteRankService(
    private val votePort: VotePort,
    private val userPort: UserPort,
    private val voteOptionsByVoteDatePort: VoteOptionsByVoteDatePort,
    private val rankCalculateService: RankCalculateService
) : VoteRankUseCase {

    override fun getVoteResultsOfTop5(date: LocalDate): VoteResultResponsesOfTop5 {
        return VoteResultResponsesOfTop5.from(getRankedVoteResults(date))
    }

    private fun getRankedVoteResults(
        date: LocalDate
    ): Map<VoteOption, List<VoteRecord>> {
        val userId = VoteServiceHelper.findLoginUserId(userPort)
        val user = VoteServiceHelper.findUser(userPort, userId)
        val classmates = VoteServiceHelper.findClassmatesByUser(userPort, user)
        val vote = VoteServiceHelper.findVoteByUser(votePort, user, date)
        val voteOptions = VoteServiceHelper.findVoteOptionsByVoteDate(voteOptionsByVoteDatePort, vote)
        val rankedVoteResults = vote.getRankedVoteResults(voteOptions, classmates, rankCalculateService)

        return rankedVoteResults
    }

    override fun getVoteResultsOfTop1(date: LocalDate): VoteResultResponsesOfTop1 {
        return VoteResultResponsesOfTop1.from(getRankedVoteResults(date))
    }

}

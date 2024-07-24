package com.wespot.vote

import com.wespot.user.User
import com.wespot.voteoption.VoteOption
import org.springframework.stereotype.Component

@Component
class ReceivedVoteCalculateService {

    fun calculateUserReceivedVotes(user: User, ballots: List<Ballot>): Map<Long, VoteRecord> {
        return ballots.filter { it.receiverId == user.id }
            .groupBy { it.voteOptionId }
            .mapValues { BallotsAggregator.of(it.key, it.value) }
            .mapValues { VoteRecord.of(user, it.value.getUserReceivedVotes(user.id)) }
            .toMap()
    }

    fun calculateUserReceivedVote(voteOption: VoteOption, user: User, ballots: List<Ballot>): VoteRecord {
        val ballotsByVoteOption = extractBallotsByVoteOption(ballots, voteOption)
        val voteRecord = BallotsAggregator.of(voteOption.id, ballotsByVoteOption)
            .getRankResults()
            .withIndex()
            .filter { it.value.userId == user.id }
            .map { (index, voteMetrics) -> VoteRecord.ofWithRate(user, index + 1, voteMetrics) }
            .firstOrNull() ?: throw IllegalArgumentException("해당 유저는 해당 질문지에 대한 투표를 받은 기록이 없습니다.")
        ballotsByVoteOption.forEach { it.receiverRead() }

        return voteRecord
    }

    private fun extractBallotsByVoteOption(
        ballots: List<Ballot>,
        voteOption: VoteOption
    ) = ballots.filter { voteOption.id == it.voteOptionId }
        .toList()

}

package com.wespot.vote

class BallotsAggregator(
    val ballots: List<Ballot>
) {

    companion object {

        fun of(voteOptionId: Long, ballots: List<Ballot>): BallotsAggregator {
            if (isBallotsForVoteOption(ballots, voteOptionId)) {
                throw IllegalArgumentException("서로 다른 질문지에 대한 결과가 섞였습니다.")
            }
            return BallotsAggregator(ballots)
        }

        private fun isBallotsForVoteOption(
            ballots: List<Ballot>,
            voteOptionId: Long
        ) = ballots.stream()
            .anyMatch { it.voteOptionId != voteOptionId }

    }

    fun getRankedResults(): List<VoteMetrics> {
        val ranked: MutableMap<Long, VoteMetrics> = LinkedHashMap()
        ballots.forEach {
            val voteMetrics =
                ranked.getOrDefault(it.receiverId, VoteMetrics.createInitialState(it.receiverId))
            ranked[it.receiverId] = voteMetrics.recordVote(it)
        }

        return calculatedRanked(ranked.map { it.value })
    }

    private fun calculatedRanked(
        ranked: List<VoteMetrics>
    ) = ranked.sortedByDescending { it.lastVotedDateTime }
        .sortedByDescending { it.voteCount }

}

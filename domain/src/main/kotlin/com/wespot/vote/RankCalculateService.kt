package com.wespot.vote

import com.wespot.user.User
import org.springframework.stereotype.Component

@Component
class RankCalculateService {

    fun calculate(users: List<User>, ballots: List<Ballot>): Map<Long, List<VoteRecord>> {
        val usersAssociateBy = users.associateBy { it.id }

        return ballots.groupBy { it.voteOptionId }
            .mapValues { BallotsAggregator.of(it.key, it.value) }
            .mapValues { entry ->
                entry.value.getRankResults()
                    .filter { usersAssociateBy.containsKey(it.userId) }
                    .map { VoteRecord.of(usersAssociateBy[it.userId]!!, it) }
            }
            .toMap(LinkedHashMap())
    }

}

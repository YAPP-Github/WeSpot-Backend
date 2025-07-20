package com.wespot.vote.port.`in`

import com.wespot.user.User
import com.wespot.voteoption.VoteOption

interface VoteOptionUsageUseCase {

    fun findLeastFrequentlyUsedVoteOptionsAt(user: User, index: Int): VoteOption

}

package com.wespot.vote.schedule

import com.wespot.vote.port.`in`.CreatedVoteUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class VoteScheduler(
    private val createdVoteUseCase: CreatedVoteUseCase
) {

    @Scheduled(cron = "0 0 0 * * *")
    fun createNewVote() {
        createdVoteUseCase.createVotes()
    }

}

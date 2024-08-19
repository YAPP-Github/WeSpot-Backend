package com.wespot.vote.schedule

import com.wespot.vote.port.`in`.CreatedVoteUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class VoteScheduler(
    private val createdVoteUseCase: CreatedVoteUseCase
) {

    @Scheduled(cron = "50 0 0 * * *") // 매일 00시 00분에 50초에 실행
    fun createNewVote() {
        createdVoteUseCase.createVotes()
    }

}

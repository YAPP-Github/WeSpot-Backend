package com.wespot.vote

import com.wespot.vote.dto.VoteOptionResponses
import com.wespot.vote.port.`in`.VoteUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/votes")
class VoteController(
    private val voteUseCase: VoteUseCase
) {

    @GetMapping("/options")
    fun getVoteOptions(userId: Long): ResponseEntity<VoteOptionResponses> {
        val responses = voteUseCase.getVoteOptions(userId);
        return ResponseEntity.ok()
            .body(responses);
    }

    @PostMapping
    fun createVote(userId: Long): ResponseEntity<Long> {
        val savedId: Long = voteUseCase.saveVote(userId);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(savedId);
    }

}
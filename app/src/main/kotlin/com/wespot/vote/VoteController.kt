package com.wespot.vote

import com.wespot.vote.dto.request.VoteRequest
import com.wespot.vote.dto.response.VoteItems
import com.wespot.vote.port.`in`.VoteUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/votes")
class VoteController(
    private val voteUseCase: VoteUseCase
) {

    @GetMapping("/options")
    fun getVoteOptions(userId: Long): ResponseEntity<VoteItems> {
        val responses = voteUseCase.getVoteOptions(userId)
        return ResponseEntity.ok()
            .body(responses)
    }

    @PostMapping
    fun createVote(userId: Long, @RequestBody requests: List<VoteRequest>): ResponseEntity<Long> {
        val savedId: Long = voteUseCase.saveVote(userId, requests)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(savedId)
    }

}
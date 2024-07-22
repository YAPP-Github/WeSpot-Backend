package com.wespot.vote

import com.wespot.vote.dto.request.VoteRequests
import com.wespot.vote.dto.response.SaveVoteResponse
import com.wespot.vote.dto.response.VoteItems
import com.wespot.vote.port.`in`.SaveVoteUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/votes")
class VoteController(
    private val saveVoteUseCase: SaveVoteUseCase
) {

    @GetMapping("/options")
    fun getVoteOptions(userId: Long): ResponseEntity<VoteItems> {
        val responses = saveVoteUseCase.getVoteOptions(userId)
        return ResponseEntity.ok(responses)
    }

    @PostMapping
    fun createVote(
        userId: Long,
        @RequestBody requests: VoteRequests
    ): ResponseEntity<SaveVoteResponse> {
        val savedId: SaveVoteResponse = saveVoteUseCase.saveVote(userId, requests)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(savedId)
    }

}

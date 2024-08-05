package com.wespot.vote

import com.wespot.vote.dto.request.VoteRequests
import com.wespot.vote.dto.response.SaveVoteResponse
import com.wespot.vote.dto.response.VoteItems
import com.wespot.vote.dto.response.received.ReceivedVoteResponse
import com.wespot.vote.dto.response.received.ReceivedVotesResponses
import com.wespot.vote.dto.response.sent.SentVoteResponse
import com.wespot.vote.dto.response.sent.SentVotesResponses
import com.wespot.vote.dto.response.top1.VoteResultResponsesOfTop1
import com.wespot.vote.dto.response.top5.VoteResultResponsesOfTop5
import com.wespot.vote.port.`in`.ReceivedVoteUseCase
import com.wespot.vote.port.`in`.SavedVoteUseCase
import com.wespot.vote.port.`in`.SentVoteUseCase
import com.wespot.vote.port.`in`.VoteRankUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/v1/votes")
class VoteController(
    private val savedVoteUseCase: SavedVoteUseCase,
    private val voteRankUseCase: VoteRankUseCase,
    private val receivedVoteUseCase: ReceivedVoteUseCase,
    private val sentVoteUseCase: SentVoteUseCase
) {

    @GetMapping("/options")
    fun getVoteOptions(): ResponseEntity<VoteItems> {
        val responses = savedVoteUseCase.getVoteOptions()
        return ResponseEntity.ok(responses)
    }

    @PostMapping
    fun createVote(
        @RequestBody requests: VoteRequests
    ): ResponseEntity<SaveVoteResponse> {
        val savedId: SaveVoteResponse = savedVoteUseCase.saveVote(requests)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(savedId)
    }

    @GetMapping
    fun getTop5VoteResults(
        @RequestParam date: LocalDate
    ): ResponseEntity<VoteResultResponsesOfTop5> {
        val responses: VoteResultResponsesOfTop5 = voteRankUseCase.getVoteResultsOfTop5(date)

        return ResponseEntity.ok(responses)
    }

    @GetMapping("/tops")
    fun getTop1VoteResults(
        @RequestParam date: LocalDate
    ): ResponseEntity<VoteResultResponsesOfTop1> {
        val responses: VoteResultResponsesOfTop1 = voteRankUseCase.getVoteResultsOfTop1(date)

        return ResponseEntity.ok(responses)
    }

    @GetMapping("/received")
    fun getReceivedVotes(): ResponseEntity<ReceivedVotesResponses> {
        val responses = receivedVoteUseCase.getReceivedVotes()

        return ResponseEntity.ok(responses)
    }

    @GetMapping("/received/options/{optionId}")
    fun getReceivedVote(
        @PathVariable optionId: Long,
        @RequestParam date: LocalDate
    ): ResponseEntity<ReceivedVoteResponse> {
        val response = receivedVoteUseCase.getReceivedVote(optionId, date)

        return ResponseEntity.ok(response)
    }

    @GetMapping("/sent")
    fun getSentVotes(): ResponseEntity<SentVotesResponses> {
        val responses = sentVoteUseCase.getSentVotes()

        return ResponseEntity.ok(responses)
    }

    @GetMapping("/sent/options/{optionId}")
    fun getSentVote(
        @PathVariable optionId: Long,
        @RequestParam date: LocalDate
    ): ResponseEntity<SentVoteResponse> {
        val response = sentVoteUseCase.getSentVote(optionId, date)

        return ResponseEntity.ok(response)
    }

}

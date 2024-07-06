package com.wespot.vote.service

import com.wespot.vote.port.`in`.VoteUseCase

class VoteService(
    private val voteUseCase: VoteUseCase
) : VoteUseCase {
}
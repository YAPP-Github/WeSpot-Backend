package com.wespot.vote.domain

import com.wespot.vote.Ballot
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class BallotTest() : BehaviorSpec({

    given("Valid한 값들로") {
        val voteId = 1L
        val voteOptionId = 1L
        val senderId = 1L
        val receiverId = 2L
        `when`("투표지를 생성할 때") {
            val ballot = Ballot.of(voteId, voteOptionId, senderId, receiverId)
            then("정상적으로 생성된다.") {
                ballot.id shouldBe null
                ballot.voteId shouldBe voteId
                ballot.voteOptionId shouldBe voteOptionId
                ballot.senderId shouldBe senderId
                ballot.receiverId shouldBe receiverId
                ballot.createdAt shouldNotBe null
                ballot.isReceiverRead shouldBe false
            }
        }
    }

    given("투표자가 본인을 투표하는") {
        val voteId = 1L
        val voteOptionId = 1L
        val senderId = 1L
        val receiverId = 1L
        `when`("투표지를 생성할 때") {
            val throwingCallable = { Ballot.of(voteId, voteOptionId, senderId, receiverId) }
            then("예외가 발생한다.") {
                val shouldThrow = shouldThrow<IllegalArgumentException>(throwingCallable)
                shouldThrow.message shouldBe "본인을 투표할 수 없습니다."
            }
        }
    }

})
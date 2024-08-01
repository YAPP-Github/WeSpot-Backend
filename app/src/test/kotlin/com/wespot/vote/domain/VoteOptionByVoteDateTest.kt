package com.wespot.vote.domain

import com.wespot.vote.VoteOptionByVoteDate
import com.wespot.voteoption.fixture.VoteOptionFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class VoteOptionByVoteDateTest : BehaviorSpec({

    given("오늘의 선택지 중 하나가") {
        val voteOption = VoteOptionFixture.createWithId(1)
        `when`("들어온 선택지 중 존재함을") {
            val voteOptionByVoteDate = VoteOptionByVoteDate(0, 0, VoteOptionFixture.createWithId(1))
            val isSameVoteOption = voteOptionByVoteDate.isSameVoteOption(voteOption.id)
            then("확인한다.") {
                isSameVoteOption shouldBe true
            }
        }
    }

})

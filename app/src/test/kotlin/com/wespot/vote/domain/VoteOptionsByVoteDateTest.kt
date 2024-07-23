package com.wespot.vote.domain

import com.wespot.vote.VoteOptionsByVoteDate
import com.wespot.voteoption.fixture.VoteOptionFixture
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import java.time.LocalDate

class VoteOptionsByVoteDateTest : BehaviorSpec({

    given("오늘의 선택지가") {
        val voteOption1 = VoteOptionFixture.create()
        val voteOption2 = VoteOptionFixture.create()
        val voteOption3 = VoteOptionFixture.create()
        val voteOption4 = VoteOptionFixture.create()
        val voteOption5 = VoteOptionFixture.create()

        `when`("5개가 아닌 경우") {
            val throwingCallable = {
                VoteOptionsByVoteDate.of(
                    LocalDate.now(),
                    listOf(voteOption1, voteOption2, voteOption3, voteOption4)
                )
            }
            then("예외가 발생한다.") {
                val shouldThrow = shouldThrow<IllegalArgumentException>(throwingCallable)
                shouldThrow shouldHaveMessage "선택지는 5개가 주어져야 합니다."
            }
        }
        `when`("미래의 선택지인 경우") {
            val throwingCallable = {
                VoteOptionsByVoteDate.of(
                    LocalDate.now().plusDays(1L),
                    listOf(voteOption1, voteOption2, voteOption3, voteOption4, voteOption5)
                )
            }
            then("예외가 발생한다.") {
                val shouldThrow = shouldThrow<IllegalArgumentException>(throwingCallable)
                shouldThrow shouldHaveMessage "미래의 선택지는 정할 수 없습니다."
            }
        }
        `when`("정상적으로 주어진 경우") {
            val voteOptionsByVoteDate = VoteOptionsByVoteDate.of(
                LocalDate.now(),
                listOf(voteOption1, voteOption2, voteOption3, voteOption4, voteOption5)
            )
            then("정상적으로 생성된다.") {
                voteOptionsByVoteDate.voteDate shouldBe LocalDate.now()
                voteOptionsByVoteDate.voteOptions.size shouldBe 5
            }
        }
    }

    given("주어진 선택지가") {
        val voteOptions = VoteOptionsByVoteDate.of(
            LocalDate.now(),
            listOf(
                VoteOptionFixture.createWithId(1L),
                VoteOptionFixture.createWithId(2L),
                VoteOptionFixture.createWithId(3L),
                VoteOptionFixture.createWithId(4L),
                VoteOptionFixture.createWithId(5L),
            )
        )
        `when`("오늘의 선택지에 포함되어 있지 않은 경우") {
            val shouldThrow = shouldThrow<IllegalArgumentException> { voteOptions.validateVoteOption(6L) }
            then("예외가 발생한다.") {
                shouldThrow shouldHaveMessage "오늘 제공된 질문지만 선택해 투표할 수 있습니다."
            }
        }
        `when`("오늘의 선택지에 포함되어 있는 경우") {
            then("예외가 발생하지 않는다.") {
                shouldNotThrow<IllegalArgumentException> { voteOptions.validateVoteOption(5L) }
            }
        }
    }

})

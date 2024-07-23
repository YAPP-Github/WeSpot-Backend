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

    given("모든 질문지 중에") {
        val voteOptions = listOf(
            VoteOptionFixture.createWithId(1),
            VoteOptionFixture.createWithId(2),
            VoteOptionFixture.createWithId(3),
            VoteOptionFixture.createWithId(4),
            VoteOptionFixture.createWithId(5),
            VoteOptionFixture.createWithId(6),
            VoteOptionFixture.createWithId(7),
            VoteOptionFixture.createWithId(8),
            VoteOptionFixture.createWithId(9),
        )

        `when`("voteNumber가 0일 때의 오늘의 질문지를") {
            val voteOptionsByVoteDate = VoteOptionsByVoteDate.of(LocalDate.now(), 0, voteOptions)

            then("정상적으로 반환한다.") {
                voteOptionsByVoteDate.voteOptions[0].id shouldBe 1
                voteOptionsByVoteDate.voteOptions[1].id shouldBe 2
                voteOptionsByVoteDate.voteOptions[2].id shouldBe 3
                voteOptionsByVoteDate.voteOptions[3].id shouldBe 4
                voteOptionsByVoteDate.voteOptions[4].id shouldBe 5
            }
        }

        `when`("오늘의 질문지를 뽑아낼 때, 범위를 벗어나는 경우") {
            val throwingCallable = { VoteOptionsByVoteDate.of(LocalDate.now(), 1, voteOptions) }

            then("예외가 발생한다.") {
                val shouldThrow = shouldThrow<IllegalArgumentException>(throwingCallable)
                shouldThrow shouldHaveMessage "선택지의 개수가 5의 배수가 아닙니다."
            }
        }
    }

    given("오늘의 선택지가") {
        val voteOption1 = VoteOptionFixture.create()
        val voteOption2 = VoteOptionFixture.create()
        val voteOption3 = VoteOptionFixture.create()
        val voteOption4 = VoteOptionFixture.create()
        val voteOption5 = VoteOptionFixture.create()

        `when`("미래의 선택지인 경우") {
            val throwingCallable = {
                VoteOptionsByVoteDate.of(
                    LocalDate.now().plusDays(1L),
                    0,
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
                0,
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
            0,
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

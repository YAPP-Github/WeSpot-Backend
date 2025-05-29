package com.wespot.vote.domain

import com.wespot.exception.CustomException
import com.wespot.user.fixture.UserFixture
import com.wespot.vote.VoteMetrics
import com.wespot.vote.VoteRecord
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage

class VoteRecordTest : BehaviorSpec({

    given("VoteMetrics와 User를 활용해") {
        val voteMetrics = VoteMetrics.createInitialState(1L)
        val user = UserFixture.createWithIdSchool(1L)
        `when`("VoteRecord를") {
            val actual = VoteRecord.of(user, voteMetrics)
            then("정상적으로 생성한다.") {
                actual.user shouldBe user
                actual.voteCount shouldBe voteMetrics.voteCount
                actual.lastVotedDateTime shouldBe voteMetrics.lastVotedDateTime
                actual.isReceiverRead shouldBe voteMetrics.isReceiverRead
            }
        }

        `when`("VoteRecord를 생성할 때, VoteMetrics내의 userId와 입력된 userId가 일치하지 않으면") {
            val invalidUser = UserFixture.createWithIdSchool(2L)
            then("예외가 발생한다.") {
                val shouldThrow = shouldThrow<CustomException> { VoteRecord.of(invalidUser, voteMetrics) }
                shouldThrow shouldHaveMessage "userId가 일치하지 않습니다."
            }
        }

        `when`("VoteRecord를 생성할 때, 등수가 0 이하이면") {
            then("예외가 발생한다.") {
                val shouldThrow =
                    shouldThrow<CustomException> { VoteRecord.ofWithRate(user, 0, voteMetrics) }
                shouldThrow shouldHaveMessage "등수는 0 이하일 수 없습니다."
            }
        }

        `when`("VoteRecord를 생성할 때, 등수도 같이 입력해") {
            val actual = VoteRecord.ofWithRate(user, 1, voteMetrics)
            then("정상적으로 생성한다.") {
                actual.user shouldBe user
                actual.rate.value shouldBe 1
                actual.voteCount shouldBe voteMetrics.voteCount
                actual.lastVotedDateTime shouldBe voteMetrics.lastVotedDateTime
                actual.isReceiverRead shouldBe voteMetrics.isReceiverRead
            }
        }
    }

})

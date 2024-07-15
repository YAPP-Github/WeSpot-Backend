package com.wespot.vote.domain

import com.wespot.vote.BallotMapper
import com.wespot.vote.fixture.BallotFixture
import com.wespot.vote.fixture.BallotJpaEntityFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class VoteTest() : BehaviorSpec({

    // 일단, 오늘 질문지
    // 넘어온 질문지의 사이즈가 5 미만이라서 예외가 터지는 경우
    // Vote가능한 유저를 찾는 것
    // addBalot할 때, 오늘 질문에포함되어 있는지 확인
    // 그렇지 않은 경우도

    given("Jpa Entity 투표지가 주어지고") {
        `when`("Mapper를 통해 이를 Domain Entity로 변환하면") {
            then("Domain Entity를 반환한다") {
            }
        }
    }

    given("Domain Entity 투표지가 주어지고") {
        `when`("Mapper를 통해 이를 Jpa Entity로 변환하면") {
            then("Jpa Entity를 반환한다") {
            }
        }
    }

})
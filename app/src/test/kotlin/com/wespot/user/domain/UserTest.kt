package com.wespot.user.domain

import com.wespot.user.fixture.RestrictionFixture
import com.wespot.user.fixture.UserFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class UserTest : BehaviorSpec({

    given("유저에게") {
        val user = UserFixture.createWithId(1L)

        `when`("제한을 주었을 때") {

            val originRestriction = user.restriction
            val newRestriction = RestrictionFixture.createFirstMessageUsageRestriction()
            user.restrict(newRestriction)

            then("상태가 변경된다.") {
                user.id shouldBe 1L
                user.restriction shouldNotBe originRestriction
                user.restriction shouldBe newRestriction
            }
        }
    }

})

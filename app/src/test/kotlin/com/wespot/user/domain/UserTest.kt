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

    given("서로 다른 유저가") {
        val user = UserFixture.createWithSchoolIdAndGradeAndClassNumber(1, 1, 1)
        `when`("같은 학급인 것을") {
            val classmate = UserFixture.createWithSchoolIdAndGradeAndClassNumber(1, 1, 1)
            val isClassmate = user.isClassmate(classmate)
            then("확인한다.") {
                isClassmate shouldBe true
            }
        }
        `when`("다른 학급인 것을") {
            val otherClassUser = UserFixture.createWithSchoolIdAndGradeAndClassNumber(1, 1, 2)
            val isNotClassmate = user.isClassmate(otherClassUser)
            then("확인한다.") {
                isNotClassmate shouldBe false
            }
        }
    }

    given("유저의") {
        val user = UserFixture.createWithId(1L)
        `when`("메시지 알림 설정을") {
            user.changeSettings(
                isEnableMessageNotification = true,
                isEnableVoteNotification = false,
                isEnableEventNotification = false
            )
            then("변경한다.") {
                user.isEnableMessageNotification() shouldBe true
                user.isEnableVoteNotification() shouldBe false
                user.isEnableEventNotification() shouldBe false
            }
        }
        `when`("투표 알림 설정을") {
            user.changeSettings(
                isEnableMessageNotification = false,
                isEnableVoteNotification = true,
                isEnableEventNotification = true
            )
            then("변경한다.") {
                user.isEnableMessageNotification() shouldBe false
                user.isEnableVoteNotification() shouldBe true
                user.isEnableEventNotification() shouldBe true
            }
        }
    }

})

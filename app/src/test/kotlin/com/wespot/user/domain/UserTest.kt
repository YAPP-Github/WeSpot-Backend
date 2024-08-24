package com.wespot.user.domain

import com.wespot.exception.CustomException
import com.wespot.user.RestrictionType
import com.wespot.user.fixture.RestrictionFixture
import com.wespot.user.fixture.UserFixture
import com.wespot.user.restriction.Restriction
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.throwable.shouldHaveMessage
import java.time.LocalDate

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
                isEnableMarketingNotification = false
            )
            then("변경한다.") {
                user.isEnableMessageNotification() shouldBe true
                user.isEnableVoteNotification() shouldBe false
                user.isEnableMarketingNotification() shouldBe false
            }
        }
        `when`("투표 알림 설정을") {
            user.changeSettings(
                isEnableMessageNotification = false,
                isEnableVoteNotification = true,
                isEnableMarketingNotification = true
            )
            then("변경한다.") {
                user.isEnableMessageNotification() shouldBe false
                user.isEnableVoteNotification() shouldBe true
                user.isEnableMarketingNotification() shouldBe true
            }
        }
    }

    given("유저가") {
        val user = UserFixture.createWithId(1L)
        `when`("탈퇴를") {
            val withdrawUser = user.withdraw()
            then("정상적으로 진행한다.") {
                withdrawUser.name shouldBe "탈퇴한 유저입니다."
            }
        }
    }

    given("유저가") {
        val user = UserFixture.createWithId(1L)
        `when`("제재가 풀렸는지") {
            val initialRestriction = Restriction.createInitialState()
            val restriction = initialRestriction.addRestrict(RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT, 30)
            user.restrict(restriction)
            val actual = user.getCurrentUserRestrictionBasedOnTime(LocalDate.now().plusDays(31))
            then("확인한다.") {
                actual.messageRestriction.restrictionType shouldBe RestrictionType.NONE
                actual.messageRestriction.releaseDate shouldBe LocalDate.of(9999, 12, 31)
            }
        }
        `when`("아직 제재중인지") {
            val actual = user.getCurrentUserRestrictionBasedOnTime(LocalDate.now().plusDays(29))
            then("확인한다.") {
                actual.messageRestriction.restrictionType shouldBe RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT
                actual.messageRestriction.releaseDate shouldBe LocalDate.now().plusDays(30)
            }
        }
    }

    given("소개에") {
        val badWordsIntroduction = "ㅅㅂㅅㅂㅅㅂㅅㅂㅅㅂㅅㅂㅂㅂㅂㅂㅂ"
        `when`("욕설이 포함되어 있는 경우") {
            val createUser = UserFixture.createWithId(1)
            val shouldThrow = shouldThrow<CustomException> { createUser.updateProfile(badWordsIntroduction) }
            then("예외가 발생한다.") {
                shouldThrow shouldHaveMessage "소개에 비속어가 포함되어 있습니다."
            }
        }
    }

})

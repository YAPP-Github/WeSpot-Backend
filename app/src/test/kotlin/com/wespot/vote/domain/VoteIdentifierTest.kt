package com.wespot.vote.domain

import com.wespot.user.fixture.UserFixture
import com.wespot.vote.VoteIdentifier
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class VoteIdentifierTest : BehaviorSpec({

    given("서로 다른 VoteIdentifier가") {
        val firstUser = UserFixture.createWithSchoolIdAndGradeAndClassNumber(1, 1, 1)
        val secondUser = UserFixture.createWithSchoolIdAndGradeAndClassNumber(1, 1, 1)
        val thirdUser = UserFixture.createWithSchoolIdAndGradeAndClassNumber(1, 1, 2)
        val today = LocalDate.now()
        val firstVoteIdentifier = VoteIdentifier.of(firstUser, today)
        val secondVoteIdentifier = VoteIdentifier.of(secondUser, today.minusDays(1))
        val thirdVoteIdentifier = VoteIdentifier.of(thirdUser, today)

        `when`("같은 학급인지") {
            val isSameClass = firstVoteIdentifier.isSameClass(secondVoteIdentifier)
            val isNotSameClass = secondVoteIdentifier.isSameClass(thirdVoteIdentifier)

            then("확인한다.") {
                isSameClass shouldBe true
                isNotSameClass shouldBe false
                firstVoteIdentifier.grade shouldBe 1
                firstVoteIdentifier.schoolId shouldBe 1
                firstVoteIdentifier.classNumber shouldBe 1
                firstVoteIdentifier.date shouldBe today
                secondVoteIdentifier.date shouldBe today.minusDays(1)
                thirdVoteIdentifier.grade shouldBe 1
                thirdVoteIdentifier.schoolId shouldBe 1
                thirdVoteIdentifier.classNumber shouldBe 2
            }
        }
    }

})

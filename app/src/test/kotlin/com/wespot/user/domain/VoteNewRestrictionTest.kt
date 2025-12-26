package com.wespot.user.domain

import com.wespot.user.RestrictionType
import com.wespot.user.fixture.VoteRestrictionFixture
import io.kotest.matchers.shouldBe
import org.junit.Test
import java.time.LocalDate

class VoteNewRestrictionTest {

    @Test
    fun `0,5,10,15,20개에서는 이용제한을 적용합니다`() {
        // given
        val voteRestriction = VoteRestrictionFixture.generate()

        // when
        val actual1 = voteRestriction.receivedNewReport(1)
        val actual2 = voteRestriction.receivedNewReport(15)

        // then
        actual1.restrictionType shouldBe RestrictionType.NONE
        actual1.releaseDate shouldBe LocalDate.of(9999, 12, 31)

        actual2.restrictionType shouldBe RestrictionType.PERMANENT_BAN_VOTE_REPORT
        actual2.releaseDate shouldBe LocalDate.of(9999, 12, 31)
    }

    @Test
    fun `이용제한이 되는 개수가 딱! 아니라면 제한에 영향을 주지 않습니다`() {
        // given
        val now = LocalDate.now()
        val voteRestriction = VoteRestrictionFixture.generate(
            restrictionType = RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT,
            releaseDate = now.plusDays(3)
        )

        // when
        val actual1 = voteRestriction.receivedNewReport(1)
        val actual2 = voteRestriction.receivedNewReport(5)
        val actual3 = voteRestriction.receivedNewReport(10)
        val actual4 = voteRestriction.receivedNewReport(14)
        val actual5 = voteRestriction.receivedNewReport(16)

        // then
        actual1 shouldBe voteRestriction
        actual2 shouldBe voteRestriction
        actual3 shouldBe voteRestriction
        actual4 shouldBe voteRestriction
        actual5 shouldBe voteRestriction
    }

}

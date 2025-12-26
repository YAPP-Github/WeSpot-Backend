package com.wespot.user.domain

import com.wespot.user.RestrictionType
import com.wespot.user.fixture.CommunityRestrictionFixture
import io.kotest.matchers.shouldBe
import org.junit.Test
import java.time.LocalDate

class CommunityRestrictionTest {

    @Test
    fun `0,5,10,15,20개에서는 이용제한을 적용합니다`() {
        // given
        val communityRestriction = CommunityRestrictionFixture.generate()

        // when
        val actual1 = communityRestriction.receivedNewReport(0)
        val actual2 = communityRestriction.receivedNewReport(5)
        val actual3 = communityRestriction.receivedNewReport(10)
        val actual4 = communityRestriction.receivedNewReport(15)
        val actual5 = communityRestriction.receivedNewReport(20)

        // then
        actual1.restrictionType shouldBe RestrictionType.NONE
        actual1.releaseDate shouldBe LocalDate.of(9999, 12, 31)

        actual2.restrictionType shouldBe RestrictionType.TEMPORARY_BAN_COMMUNITY_REPORT
        actual2.releaseDate shouldBe LocalDate.now().plusDays(3)

        actual3.restrictionType shouldBe RestrictionType.TEMPORARY_BAN_COMMUNITY_REPORT
        actual3.releaseDate shouldBe LocalDate.now().plusDays(7)

        actual4.restrictionType shouldBe RestrictionType.TEMPORARY_BAN_COMMUNITY_REPORT
        actual4.releaseDate shouldBe LocalDate.now().plusDays(15)

        actual5.restrictionType shouldBe RestrictionType.PERMANENT_BAN_COMMUNITY_REPORT
        actual5.releaseDate shouldBe LocalDate.of(9999, 12, 31)
    }

    @Test
    fun `이용제한이 되는 개수가 딱! 아니라면 제한에 영향을 주지 않습니다`() {
        // given
        val now = LocalDate.now()
        val communityRestriction = CommunityRestrictionFixture.generate(
            restrictionType = RestrictionType.TEMPORARY_BAN_COMMUNITY_REPORT,
            releaseDate = now.plusDays(3)
        )

        // when
        val actual1 = communityRestriction.receivedNewReport(1)
        val actual2 = communityRestriction.receivedNewReport(6)
        val actual3 = communityRestriction.receivedNewReport(11)
        val actual4 = communityRestriction.receivedNewReport(16)
        val actual5 = communityRestriction.receivedNewReport(21)

        // then
        actual1 shouldBe communityRestriction
        actual2 shouldBe communityRestriction
        actual3 shouldBe communityRestriction
        actual4 shouldBe communityRestriction
        actual5 shouldBe communityRestriction
    }

}

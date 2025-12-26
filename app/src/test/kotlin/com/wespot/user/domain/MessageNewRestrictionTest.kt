package com.wespot.user.domain

import com.wespot.user.RestrictionType
import com.wespot.user.fixture.MessageRestrictionFixture
import io.kotest.matchers.shouldBe
import org.junit.Test
import java.time.LocalDate

class MessageNewRestrictionTest {


    @Test
    fun `0,5,10,15,20개에서는 이용제한을 적용합니다`() {
        // given
        val messageRestriction = MessageRestrictionFixture.generate()

        // when
        val actual1 = messageRestriction.receivedNewReport(0)
        val actual2 = messageRestriction.receivedNewReport(5)
        val actual3 = messageRestriction.receivedNewReport(10)
        val actual4 = messageRestriction.receivedNewReport(15)
        val actual5 = messageRestriction.receivedNewReport(20)

        // then
        actual1.restrictionType shouldBe RestrictionType.NONE
        actual1.releaseDate shouldBe LocalDate.of(9999, 12, 31)

        actual2.restrictionType shouldBe RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT
        actual2.releaseDate shouldBe LocalDate.now().plusDays(3)

        actual3.restrictionType shouldBe RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT
        actual3.releaseDate shouldBe LocalDate.now().plusDays(7)

        actual4.restrictionType shouldBe RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT
        actual4.releaseDate shouldBe LocalDate.now().plusDays(15)

        actual5.restrictionType shouldBe RestrictionType.PERMANENT_BAN_MESSAGE_REPORT
        actual5.releaseDate shouldBe LocalDate.of(9999, 12, 31)
    }

    @Test
    fun `이용제한이 되는 개수가 딱! 아니라면 제한에 영향을 주지 않습니다`() {
        // given
        val now = LocalDate.now()
        val messageRestriction = MessageRestrictionFixture.generate(
            restrictionType = RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT,
            releaseDate = now.plusDays(3)
        )

        // when
        val actual1 = messageRestriction.receivedNewReport(1)
        val actual2 = messageRestriction.receivedNewReport(6)
        val actual3 = messageRestriction.receivedNewReport(11)
        val actual4 = messageRestriction.receivedNewReport(16)
        val actual5 = messageRestriction.receivedNewReport(21)

        // then
        actual1 shouldBe messageRestriction
        actual2 shouldBe messageRestriction
        actual3 shouldBe messageRestriction
        actual4 shouldBe messageRestriction
        actual5 shouldBe messageRestriction
    }

}

package com.wespot.report.domain

import com.wespot.exception.CustomException
import com.wespot.report.Report
import com.wespot.report.ReportType
import com.wespot.user.fixture.UserFixture
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage

class ReportTest : BehaviorSpec({

    given("신고를 생성할 때") {
        val sender = UserFixture.createWithId(1L)
        val receiver = UserFixture.createWithId(1L)

        `when`("송신자와 수신자가 동일하면 ") {
            val shouldThrow =
                shouldThrow<CustomException> { Report.of(ReportType.MESSAGE, 1L, sender, null, receiver) }

            then("예외가 발생한다.") {
                shouldThrow shouldHaveMessage "본인이 본인을 신고할 수 없습니다."
            }
        }
    }

    given("신고의") {
        `when`("타입이 Message 인지") {
            val report =
                Report.of(ReportType.MESSAGE, 1L, UserFixture.createWithId(1L), null, UserFixture.createWithId(2L))
            val messageReport = report.isMessageReport()
            val voteReport = report.isVoteReport()
            then("확인한다.") {
                messageReport shouldBe true
                voteReport shouldBe false
            }
        }
        `when`("타입이 Vote 인지") {
            val report =
                Report.of(ReportType.VOTE, 1L, UserFixture.createWithId(1L), null, UserFixture.createWithId(2L))
            val messageReport = report.isMessageReport()
            val voteReport = report.isVoteReport()

            then("확인한다.") {
                messageReport shouldBe false
                voteReport shouldBe true
            }
        }
    }

    given("서로 다른 신고가") {
        val sameReport1 =
            Report.of(ReportType.MESSAGE, 1L, UserFixture.createWithId(1L), null, UserFixture.createWithId(2L))
        val sameReport2 =
            Report.of(ReportType.MESSAGE, 1L, UserFixture.createWithId(1L), null, UserFixture.createWithId(2L))
        val differenceReport =
            Report.of(ReportType.VOTE, 1L, UserFixture.createWithId(1L), null, UserFixture.createWithId(2L))
        `when`("같은 신고인지") {
            val same = sameReport1.isSameReport(sameReport2)
            val difference = sameReport1.isSameReport(differenceReport)
            then("확인한다.") {
                same shouldBe true
                difference shouldBe false
            }
        }
    }

})

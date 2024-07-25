package com.wespot.report.domain

import com.wespot.report.Report
import com.wespot.report.ReportType
import com.wespot.report.RestrictionService
import com.wespot.report.fixture.ReportFixture
import com.wespot.user.Restriction
import com.wespot.user.RestrictionType
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import java.time.LocalDate

class RestrictionServiceTest : BehaviorSpec({

    given("RestrictionService") {
        val restrictionService = RestrictionService()

        `when`("쪽지에 대한 신고가 4개가 들어오더라도 제한은") {
            val originRestriction = Restriction.createInitialState()
            val reports = getReportByCount(ReportType.MESSAGE, 3)
            val report =
                ReportFixture.createWithReportTypeAndTargetIdAndSenderIdAndReceiverId(ReportType.MESSAGE, 4, 1, 2)
            val newRestriction = restrictionService.calculateRestrictionByReports(originRestriction, report, reports)

            then("변경되지 않는다.") {
                originRestriction.restrictionType shouldBe newRestriction.restrictionType
                originRestriction.releaseDate shouldBe newRestriction.releaseDate
            }
        }
        `when`("쪽지에 대한 신고가 5개가 들어오면") {
            val originRestriction = Restriction.createInitialState()
            val reports = getReportByCount(ReportType.MESSAGE, 4)
            val report =
                ReportFixture.createWithReportTypeAndTargetIdAndSenderIdAndReceiverId(ReportType.MESSAGE, 5, 1, 2)
            val newRestriction = restrictionService.calculateRestrictionByReports(originRestriction, report, reports)

            then("30일 이용제한이 된다.") {
                newRestriction.restrictionType shouldBe RestrictionType.MESSAGE_USAGE
                newRestriction.releaseDate shouldBe LocalDate.now().plusDays(30)
            }
        }
        `when`("쪽지에 대한 신고가 9개가 들어오더라도 제한은") {
            val originRestriction = Restriction.createInitialState()
            val reports = getReportByCount(ReportType.MESSAGE, 8)
            val report =
                ReportFixture.createWithReportTypeAndTargetIdAndSenderIdAndReceiverId(ReportType.MESSAGE, 9, 1, 2)
            val newRestriction = restrictionService.calculateRestrictionByReports(originRestriction, report, reports)

            then("변경되지 않는다.") {
                originRestriction.restrictionType shouldBe newRestriction.restrictionType
                originRestriction.releaseDate shouldBe newRestriction.releaseDate
            }
        }
        `when`("쪽지에 대한 신고가 10개가 들어오면") {
            val originRestriction = Restriction.createInitialState()
            val reports = getReportByCount(ReportType.MESSAGE, 9)
            val report =
                ReportFixture.createWithReportTypeAndTargetIdAndSenderIdAndReceiverId(ReportType.MESSAGE, 10, 1, 2)
            val newRestriction = restrictionService.calculateRestrictionByReports(originRestriction, report, reports)

            then("90일 이용제한이 된다.") {
                newRestriction.restrictionType shouldBe RestrictionType.MESSAGE_USAGE
                newRestriction.releaseDate shouldBe LocalDate.now().plusDays(90)
            }
        }
        `when`("쪽지에 대한 신고가 14개가 들어오더라도 제한은") {
            val originRestriction = Restriction.createInitialState()
            val reports = getReportByCount(ReportType.MESSAGE, 13)
            val report =
                ReportFixture.createWithReportTypeAndTargetIdAndSenderIdAndReceiverId(ReportType.MESSAGE, 14, 1, 2)
            val newRestriction = restrictionService.calculateRestrictionByReports(originRestriction, report, reports)

            then("변경되지 않는다.") {
                originRestriction.restrictionType shouldBe newRestriction.restrictionType
                originRestriction.releaseDate shouldBe newRestriction.releaseDate
            }
        }
        `when`("쪽지에 대한 신고가 15개가 들어오면") {
            val originRestriction = Restriction.createInitialState()
            val reports = getReportByCount(ReportType.MESSAGE, 14)
            val report =
                ReportFixture.createWithReportTypeAndTargetIdAndSenderIdAndReceiverId(ReportType.MESSAGE, 15, 1, 2)
            val newRestriction = restrictionService.calculateRestrictionByReports(originRestriction, report, reports)

            then("영구 이용제한 된다.") {
                newRestriction.restrictionType shouldBe RestrictionType.MESSAGE_PERMANENT
                newRestriction.releaseDate shouldBe LocalDate.MAX
            }
        }
        `when`("투표에 대한 신고가 14개가 들어오더라도 제한은") {
            val originRestriction = Restriction.createInitialState()
            val reports = getReportByCount(ReportType.VOTE, 13)
            val report =
                ReportFixture.createWithReportTypeAndTargetIdAndSenderIdAndReceiverId(ReportType.VOTE, 14, 1, 2)
            val newRestriction = restrictionService.calculateRestrictionByReports(originRestriction, report, reports)

            then("변경되지 않는다.") {
                originRestriction.restrictionType shouldBe newRestriction.restrictionType
                originRestriction.releaseDate shouldBe newRestriction.releaseDate
            }
        }
        `when`("투표에 대한 신고가 15개가 들어오면") {
            val originRestriction = Restriction.createInitialState()
            val reports = getReportByCount(ReportType.VOTE, 14)
            val report =
                ReportFixture.createWithReportTypeAndTargetIdAndSenderIdAndReceiverId(ReportType.VOTE, 15, 1, 2)
            val newRestriction = restrictionService.calculateRestrictionByReports(originRestriction, report, reports)

            then("영구 이용제한 된다.") {
                newRestriction.restrictionType shouldBe RestrictionType.VOTE_PERMANENT
                newRestriction.releaseDate shouldBe LocalDate.MAX
            }
        }
        `when`("기존의 제한과 상관없이 현재 이용 제한 원칙에 따라") {
            val originRestriction = Restriction.of(RestrictionType.MESSAGE_USAGE, 30)
            val reports = getReportByCount(ReportType.MESSAGE, 9)
            val report =
                ReportFixture.createWithReportTypeAndTargetIdAndSenderIdAndReceiverId(ReportType.MESSAGE, 10, 1, 2)
            val newRestriction = restrictionService.calculateRestrictionByReports(originRestriction, report, reports)

            then("이용제한이 결정된다.") {
                newRestriction.restrictionType shouldBe RestrictionType.MESSAGE_USAGE
                newRestriction.releaseDate shouldBe LocalDate.now().plusDays(90)
            }
        }
        `when`("서로 다른 타입의 신고가 들어오면") {
            val originRestriction = Restriction.createInitialState()
            val reports = getReportByCount(ReportType.MESSAGE, 9)
            val report =
                ReportFixture.createWithReportTypeAndTargetIdAndSenderIdAndReceiverId(ReportType.VOTE, 10, 1, 2)

            then("예외가 발생한다.") {
                val shouldThrow = shouldThrow<IllegalArgumentException> {
                    restrictionService.calculateRestrictionByReports(
                        originRestriction,
                        report,
                        reports
                    )
                }
                shouldThrow shouldHaveMessage "새로 들어온 신고의 타입과 동일한 신고들을 다루어야합니다."
            }
        }
        `when`("동일한 쪽지를 2번 이상 신고하면") {
            val originRestriction = Restriction.createInitialState()
            val reports = getReportByCount(ReportType.MESSAGE, 9)
            val report =
                ReportFixture.createWithReportTypeAndTargetIdAndSenderIdAndReceiverId(ReportType.MESSAGE, 1, 1, 2)

            then("예외가 발생한다.") {
                val shouldThrow = shouldThrow<IllegalArgumentException> {
                    restrictionService.calculateRestrictionByReports(
                        originRestriction,
                        report,
                        reports
                    )
                }
                shouldThrow shouldHaveMessage "쪽지 하나를 여러 번 신고할 수 없습니다."
            }
        }
        `when`("투표일 때에는 동일한 사람이 동일한 사람을 2번 이상 제보하더라도") {
            val originRestriction = Restriction.createInitialState()
            val reports = getReportByCount(ReportType.VOTE, 9)
            val report = ReportFixture.createWithReportTypeAndTargetIdAndSenderIdAndReceiverId(ReportType.VOTE, 1, 1, 2)

            then("예외가 발생하지 않는다.") {
                shouldNotThrow<IllegalArgumentException> {
                    restrictionService.calculateRestrictionByReports(
                        originRestriction,
                        report,
                        reports
                    )
                }
            }
        }
    }

})

private fun getReportByCount(reportType: ReportType, count: Long): MutableList<Report> {
    val reports = mutableListOf<Report>()
    for (i in 1..count) {
        reports.add(ReportFixture.createWithReportTypeAndTargetIdAndSenderIdAndReceiverId(reportType, i, 1, 2))
    }
    return reports
}

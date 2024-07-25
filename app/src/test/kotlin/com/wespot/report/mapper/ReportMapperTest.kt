package com.wespot.report.mapper

import com.wespot.report.ReportMapper
import com.wespot.report.ReportType
import com.wespot.report.fixture.ReportFixture
import com.wespot.report.fixture.ReportJpaEntityFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class ReportMapperTest : BehaviorSpec({

    given("ReportMapper를 사용하여") {
        `when`("DomainEntity를 JpaEntity로 변경하는 경우") {
            val domainEntity = ReportFixture.createWithReportType(ReportType.VOTE)
            val jpaEntity = ReportMapper.toJpaEntity(domainEntity)

            then("정상적으로 변환된다.") {
                domainEntity.id shouldBe jpaEntity.id
                domainEntity.reportType shouldBe ReportType.VOTE
                domainEntity.targetId shouldBe jpaEntity.targetId
                domainEntity.senderId shouldBe jpaEntity.senderId
                domainEntity.receiverId shouldBe jpaEntity.receiverId
                domainEntity.createdAt shouldBe jpaEntity.baseEntity.createdAt
            }
        }

        `when`("JpaEntity를 DomainEntity로 변경하는 경우") {
            val jpaEntity = ReportJpaEntityFixture.createWithReportType(ReportType.VOTE)
            val domainEntity = ReportMapper.toDomainEntity(jpaEntity)

            then("정상적으로 변환된다.") {
                jpaEntity.id shouldBe domainEntity.id
                jpaEntity.reportType shouldBe ReportType.VOTE
                jpaEntity.targetId shouldBe domainEntity.targetId
                jpaEntity.senderId shouldBe domainEntity.senderId
                jpaEntity.receiverId shouldBe domainEntity.receiverId
                jpaEntity.baseEntity.createdAt shouldBe domainEntity.createdAt
            }
        }
    }

})

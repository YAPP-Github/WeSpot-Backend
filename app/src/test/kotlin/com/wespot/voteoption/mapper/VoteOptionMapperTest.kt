package com.wespot.voteoption.mapper

import com.wespot.voteoption.VoteOptionMapper
import com.wespot.voteoption.fixture.VoteOptionFixture
import com.wespot.voteoption.fixture.VoteOptionJpaEntityFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class VoteOptionMapperTest : BehaviorSpec({

    given("Jpa Entity 선택지가 주어지고") {
        val jpaEntity = VoteOptionJpaEntityFixture.createMock()
        `when`("Mapper를 통해 이를 Domain Entity로 변환하면") {
            val domainEntity = VoteOptionMapper.mapToDomainEntity(jpaEntity)
            then("Domain Entity를 반환한다") {
                domainEntity.id shouldBe jpaEntity.id
                domainEntity.content shouldBe jpaEntity.content
                domainEntity.createdAt shouldBe jpaEntity.baseEntity.createdAt
                domainEntity.updatedAt shouldBe jpaEntity.baseEntity.updatedAt
            }
        }
    }

    given("Domain Entity 선택지가 주어지고") {
        val domainEntity = VoteOptionFixture.createMock()
        `when`("Mapper를 통해 이를 Jpa Entity로 변환하면") {
            val jpaEntity = VoteOptionMapper.mapToJpaEntity(domainEntity)
            then("Jpa Entity를 반환한다") {
                jpaEntity.id shouldBe domainEntity.id
                jpaEntity.content shouldBe domainEntity.content
                jpaEntity.baseEntity.createdAt shouldBe domainEntity.createdAt
                jpaEntity.baseEntity.updatedAt shouldBe domainEntity.updatedAt
            }
        }
    }

})
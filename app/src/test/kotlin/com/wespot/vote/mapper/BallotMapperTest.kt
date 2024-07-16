package com.wespot.vote.mapper

import com.wespot.vote.BallotMapper
import com.wespot.vote.fixture.BallotFixture
import com.wespot.vote.fixture.BallotJpaEntityFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class BallotMapperTest : BehaviorSpec({

    given("Jpa Entity 투표지가 주어지고") {
        val jpaEntity = BallotJpaEntityFixture.create()
        `when`("Mapper를 통해 이를 Domain Entity로 변환하면") {
            val domainEntity = BallotMapper.mapToDomainEntity(jpaEntity)
            then("Domain Entity를 반환한다") {
                jpaEntity.id shouldBe domainEntity.id
                jpaEntity.voteId shouldBe domainEntity.voteId
                jpaEntity.voteOptionId shouldBe domainEntity.voteOptionId
                jpaEntity.senderId shouldBe domainEntity.senderId
                jpaEntity.receiverId shouldBe domainEntity.receiverId
                jpaEntity.baseEntity.createdAt shouldBe domainEntity.createdAt
                jpaEntity.baseEntity.updatedAt shouldBe null
                jpaEntity.isReceiverRead shouldBe domainEntity.isReceiverRead
            }
        }
    }

    given("Domain Entity 투표지가 주어지고") {
        val domainEntity = BallotFixture.create()
        `when`("Mapper를 통해 이를 Jpa Entity로 변환하면") {
            val jpaEntity = BallotMapper.mapToJpaEntity(domainEntity)
            then("Jpa Entity를 반환한다") {
                domainEntity.id shouldBe jpaEntity.id
                domainEntity.voteId shouldBe jpaEntity.voteId
                domainEntity.voteOptionId shouldBe jpaEntity.voteOptionId
                domainEntity.senderId shouldBe jpaEntity.senderId
                domainEntity.receiverId shouldBe jpaEntity.receiverId
                domainEntity.createdAt shouldBe jpaEntity.baseEntity.createdAt
                domainEntity.isReceiverRead shouldBe jpaEntity.isReceiverRead
            }
        }
    }

})

package com.wespot.vote

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class VoteMapperTest : BehaviorSpec({

    given("Jpa Entity 투표가 주어지고") {
        val voteJpaEntity = VoteJpaEntityFixture.createMock()
        val ballotJpaEntity = BallotJpaEntityFixture.createMock()
        `when`("Mapper를 통해 이를 Domain Entity로 변환하면") {
            val voteDomainEntity = VoteMapper.mapToDomainEntity(
                voteJpaEntity,
                listOf(BallotMapper.mapToDomainEntity(ballotJpaEntity))
            )
            then("Domain Entity를 반환한다") {
                voteDomainEntity.id shouldBe voteJpaEntity.id
                voteDomainEntity.schoolId shouldBe voteJpaEntity.schoolId
                voteDomainEntity.grade shouldBe voteJpaEntity.grade
                voteDomainEntity.groupNumber shouldBe voteJpaEntity.groupNumber
                voteDomainEntity.voteNumber shouldBe voteJpaEntity.voteNumber
                voteDomainEntity.date shouldBe voteJpaEntity.date
                voteDomainEntity.ballots shouldBe Ballots::class
            }
        }
    }

    given("Domain Entity 투표가 주어지고") {
        val domainEntity = VoteFixture.createMock()
        `when`("Mapper를 통해 이를 Jpa Entity로 변환하면") {
            val jpaEntity = VoteMapper.mapToJpaEntity(domainEntity)
            then("Jpa Entity를 반환한다") {
                jpaEntity.id shouldBe domainEntity.id
                jpaEntity.schoolId shouldBe domainEntity.schoolId
                jpaEntity.grade shouldBe domainEntity.grade
                jpaEntity.groupNumber shouldBe domainEntity.groupNumber
                jpaEntity.voteNumber shouldBe domainEntity.voteNumber
                jpaEntity.date shouldBe domainEntity.date
            }
        }
    }

})
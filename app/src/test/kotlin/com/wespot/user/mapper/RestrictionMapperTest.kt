package com.wespot.user.mapper

import com.wespot.user.restriction.Restriction
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class RestrictionMapperTest : BehaviorSpec({

    given("Restriction Mapper를 활용하여") {
        val restrictionDomainEntity = Restriction.createInitialState()
        `when`("도메인 엔티티를 JPA 엔티티로 매핑하면") {
            val restrictionJpaEntity = RestrictionMapper.mapToJpaEntity(restrictionDomainEntity)
            then("JPA 엔티티가 반환된다.") {
                restrictionJpaEntity.id shouldBe restrictionDomainEntity.id
                restrictionJpaEntity.messageRestrictionType shouldBe restrictionDomainEntity.messageRestriction.restrictionType
                restrictionJpaEntity.messageReleaseDate shouldBe restrictionDomainEntity.messageRestriction.releaseDate
                restrictionJpaEntity.voteRestrictionType shouldBe restrictionDomainEntity.voteRestriction.restrictionType
                restrictionJpaEntity.voteReleaseDate shouldBe restrictionDomainEntity.voteRestriction.releaseDate
            }
        }
        val restrictionJpaEntity = RestrictionMapper.mapToJpaEntity(restrictionDomainEntity)
        `when`("JPA 엔티티를 도메인 엔티티로 매핑하면") {
            val mappedRestrictionDomainEntity = RestrictionMapper.mapToDomainEntity(restrictionJpaEntity)
            then("도메인 엔티티가 반환된다.") {
                mappedRestrictionDomainEntity.id shouldBe restrictionDomainEntity.id
                mappedRestrictionDomainEntity.messageRestriction.restrictionType shouldBe restrictionDomainEntity.messageRestriction.restrictionType
                mappedRestrictionDomainEntity.messageRestriction.releaseDate shouldBe restrictionDomainEntity.messageRestriction.releaseDate
                mappedRestrictionDomainEntity.voteRestriction.restrictionType shouldBe restrictionDomainEntity.voteRestriction.restrictionType
                mappedRestrictionDomainEntity.voteRestriction.releaseDate shouldBe restrictionDomainEntity.voteRestriction.releaseDate
            }
        }
    }
})

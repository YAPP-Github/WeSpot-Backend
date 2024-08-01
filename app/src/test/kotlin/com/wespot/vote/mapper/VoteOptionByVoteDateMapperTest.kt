package com.wespot.vote.mapper

import com.wespot.vote.VoteOptionByVoteDateJpaEntity
import com.wespot.vote.VoteOptionByVoteDateMapper
import com.wespot.vote.fixture.VoteOptionByVoteDateFixture
import com.wespot.voteoption.fixture.VoteOptionFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class VoteOptionByVoteDateMapperTest : BehaviorSpec({

    given("Jpa Entity 오늘의 선택지가 주어지고") {
        val voteOption = VoteOptionFixture.createWithId(1)
        val voteOptionByVoteDateJpaEntity = VoteOptionByVoteDateJpaEntity(0, 0, voteOption.id)
        `when`("Mapper를 통해 이를 Domain Entity로 변환하면") {
            val voteOptionDomainEntity =
                VoteOptionByVoteDateMapper.mapToDomainEntity(voteOptionByVoteDateJpaEntity, voteOption)
            then("Domain Entity를 반환한다") {
                voteOptionDomainEntity.id shouldBe 0
                voteOptionDomainEntity.voteId shouldBe 0
                voteOptionDomainEntity.voteOption shouldBe voteOption
            }
        }
    }

    given("Domain Entity 오늘의 선택지가 주어지고") {
        val voteOptionDomainEntity = VoteOptionByVoteDateFixture.createWithVoteId(1)
        `when`("Mapper를 통해 이를 Jpa Entity로 변환하면") {
            val voteOptionJpaEntity = VoteOptionByVoteDateMapper.mapToJpaEntity(1, voteOptionDomainEntity)

            then("Jpa Entity를 반환한다") {
                voteOptionJpaEntity.id shouldBe voteOptionDomainEntity.id
                voteOptionJpaEntity.voteId shouldBe 1
                voteOptionJpaEntity.voteOptionId shouldBe voteOptionDomainEntity.voteOption.id
            }
        }
    }

})

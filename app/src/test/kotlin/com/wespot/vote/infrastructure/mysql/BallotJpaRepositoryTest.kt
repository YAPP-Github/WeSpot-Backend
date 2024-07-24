package com.wespot.vote.infrastructure.mysql

import com.wespot.vote.BallotJpaRepository
import com.wespot.vote.BallotMapper
import com.wespot.vote.fixture.BallotFixture
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.transaction.annotation.Transactional

@DataJpaTest
class BallotJpaRepositoryTest @Autowired constructor(
    var ballotJpaRepository: BallotJpaRepository,
) {

    @Test
    @Transactional
    fun `투표지를 저장할 때, 이미 저장된 투표지는 업데이트되고, 새로 생긴 투표지는 저장된다`() {
        // given
        val ballot1 = ballotJpaRepository.save(BallotMapper.mapToJpaEntity(BallotFixture.create()))
        val ballot2 = ballotJpaRepository.save(BallotMapper.mapToJpaEntity(BallotFixture.create()))

        // when
        val modifiedBallot = BallotMapper.mapToDomainEntity(ballot2)
        modifiedBallot.receiverRead()
        val modifiedBallot2 = BallotMapper.mapToJpaEntity(modifiedBallot)
        ballotJpaRepository.save(ballot1)
        ballotJpaRepository.save(modifiedBallot2)
        ballotJpaRepository.save(BallotMapper.mapToJpaEntity(BallotFixture.create()))

        // then
        ballotJpaRepository.findAll().size shouldBe 3
    }

}

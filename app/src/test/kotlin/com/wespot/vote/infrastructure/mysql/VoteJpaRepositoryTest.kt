package com.wespot.vote.infrastructure.mysql

import com.wespot.vote.VoteJpaRepository
import com.wespot.vote.fixture.VoteJpaEntityFixture
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDate

@DataJpaTest
class VoteJpaRepositoryTest @Autowired constructor(
    private val voteJpaRepository: VoteJpaRepository
) {

    @Test
    fun `최근 투표 목록을 받아올 때 Cursor Based Pagination을 활용한다`() {
        // given
        val now = LocalDate.now()
        val vote1 =
            voteJpaRepository.save(VoteJpaEntityFixture.createWithSchoolIdAndGradeAndClassNumberAndDate(1, 1, 1, now.minusDays(4)))
        val vote2 = voteJpaRepository.save(
            VoteJpaEntityFixture.createWithSchoolIdAndGradeAndClassNumberAndDate(
                1,
                1,
                1,
                now.minusDays(3)
            )
        )
        val vote3 = voteJpaRepository.save(
            VoteJpaEntityFixture.createWithSchoolIdAndGradeAndClassNumberAndDate(
                1,
                1,
                1,
                now.minusDays(2)
            )
        )
        val vote4 = voteJpaRepository.save(
            VoteJpaEntityFixture.createWithSchoolIdAndGradeAndClassNumberAndDate(
                1,
                1,
                1,
                now.minusDays(1)
            )
        )
        val vote5 = voteJpaRepository.save(
            VoteJpaEntityFixture.createWithSchoolIdAndGradeAndClassNumberAndDate(
                1,
                1,
                1,
                now
            )
        )

        // when
        val firstSearch =
            voteJpaRepository.findAllBySchoolIdAndGradeAndClassNumberOrderByDateDesc(1, 1, 1, Long.MAX_VALUE, 3)
        val secondSearch =
            voteJpaRepository.findAllBySchoolIdAndGradeAndClassNumberOrderByDateDesc(1, 1, 1, firstSearch[2].id, 3)

        // then
        firstSearch.size shouldBe 3
        firstSearch[0] shouldBe vote5
        firstSearch[1] shouldBe vote4
        firstSearch[2] shouldBe vote3
        secondSearch.size shouldBe 2
        secondSearch[0] shouldBe vote2
        secondSearch[1] shouldBe vote1
    }

}

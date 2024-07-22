package com.wespot.vote.presentation

import com.wespot.common.presentation.IntegrationTest
import com.wespot.user.fixture.UserFixture
import com.wespot.user.mapper.UserMapper
import com.wespot.user.repository.UserJpaRepository
import com.wespot.vote.BallotJpaRepository
import com.wespot.vote.VoteJpaRepository
import com.wespot.vote.VoteMapper
import com.wespot.vote.fixture.VoteFixture
import com.wespot.voteoption.VoteOptionJpaEntity
import com.wespot.voteoption.VoteOptionJpaRepository
import com.wespot.voteoption.VoteOptionMapper
import com.wespot.voteoption.fixture.VoteOptionFixture
import io.kotest.matchers.shouldBe
import io.restassured.RestAssured
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import java.util.*

class VoteControllerTest(
    @Autowired
    private var userJpaRepository: UserJpaRepository,
    @Autowired
    private var voteOptionJpaRepository: VoteOptionJpaRepository,
    @Autowired
    private var voteJpaRepository: VoteJpaRepository,
    @Autowired
    private var ballotJpaRepository: BallotJpaRepository,
) : IntegrationTest() { // TODO : 회원가입 구현하신 거 보고 바로 인수테스트 구현할게요

//    @Test
//    fun `질문지를 반환한다`() {
//        val voteOptions = mutableListOf<VoteOptionJpaEntity>()
//        val userJpaEntity = userJpaRepository.save(
//            UserMapper.mapToJpaEntity(
//                UserFixture.createWithIdAndEmail(
//                    0,
//                    "TestEmail@Kakako"
//                )
//            )
//        )
//        val loginUser = UserMapper.mapToDomainEntity(userJpaEntity)
//        UserFixture.setSecurityContextUser(loginUser)
//        for (i in 0 until 5) {
//            val voteOptionJpaEntity =
//                VoteOptionMapper.mapToJpaEntity(VoteOptionFixture.createWithId(0))
//            voteOptions.add(voteOptionJpaRepository.save(voteOptionJpaEntity))
//        }
//        val vote =
//            VoteFixture.createWithIdAndVoteNumberAndBallots(0, 0, Collections.emptyList())
//        voteJpaRepository.save(VoteMapper.mapToJpaEntity(vote))
//
//        val response = RestAssured.given().log().all()
//            .accept(MediaType.APPLICATION_JSON_VALUE)
//            .`when`().get("/v1/votes/options")
//            .then().log().all()
//            .extract()
//
//        response.statusCode() shouldBe 200
//    }

}

package com.wespot.vote.presentation

import com.wespot.common.presentation.IntegrationTest
import com.wespot.user.repository.UserJpaRepository
import com.wespot.vote.BallotJpaRepository
import com.wespot.vote.VoteJpaRepository
import com.wespot.voteoption.VoteOptionJpaRepository
import org.springframework.beans.factory.annotation.Autowired

class VoteControllerTest @Autowired constructor(
    private var userJpaRepository: UserJpaRepository,
    private var voteOptionJpaRepository: VoteOptionJpaRepository,
    private var voteJpaRepository: VoteJpaRepository,
    private var ballotJpaRepository: BallotJpaRepository,
) : IntegrationTest() {

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
//
//    @Test
//    fun `투표를 진행한다`(){
//        // given
//
//        // when
//
//        // then
//    }
//
//    @Test
//    fun `투표의 1~5등을 조회한다`(){
//        // given
//
//        // when
//
//        // then
//    }
//
//    @Test
//    fun `투표의 1등을 조회한다`(){
//        // given
//
//        // when
//
//        // then
//    }
//
//    @Test
//    fun `내가 받은 투표 목록을 조회한다`(){
//        // given
//
//        // when
//
//        // then
//    }
//
//    @Test
//    fun `내가 받은 투표를 개별 조회한다`(){
//        // given
//
//        // when
//
//        // then
//    }
//
//    @Test
//    fun `내가 보낸 투표 목록을 조회한다`(){
//        // given
//
//        // when
//
//        // then
//    }
//
//    @Test
//    fun `내가 보낸 투표를 개별 조회한다`(){
//        // given
//
//        // when
//
//        // then
//    }

}

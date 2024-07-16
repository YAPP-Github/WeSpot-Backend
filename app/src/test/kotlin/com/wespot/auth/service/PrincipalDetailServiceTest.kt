package com.wespot.auth.service

import com.wespot.user.fixture.UserFixture
import com.wespot.user.port.out.UserPort
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class PrincipalDetailServiceTest: BehaviorSpec({
    val userPort = mockk<UserPort>()
    val principalDetailService = PrincipalDetailService(userPort)

    given("principalDetailService 테스트") {

        val user = UserFixture.createWithId(1)

        `when`("유저 아이디로 유저 정보를 가져올 때") {
            every { userPort.findByEmail(user.email) } returns user

            val userDetails = principalDetailService.loadUserByUsername(user.email)

            then("userDetails 정보를 감싼 유저 정보를 가져와야 한다") {

                userDetails.username shouldBe user.email
                userDetails.password shouldBe user.password

            }
        }
    }

})

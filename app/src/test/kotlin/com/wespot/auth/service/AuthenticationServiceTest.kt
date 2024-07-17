package com.wespot.auth.service

import com.wespot.auth.JwtTokenInfo.EMAIL_CLAIM
import com.wespot.auth.PrincipalDetails
import com.wespot.auth.service.jwt.JwtTokenValidator
import com.wespot.user.fixture.UserFixture
import io.jsonwebtoken.Claims
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.security.core.Authentication

class AuthenticationServiceTest : BehaviorSpec({
    val principalDetailService = mockk<PrincipalDetailService>()
    val jwtTokenValidator = mockk<JwtTokenValidator>()
    val authenticationService = AuthenticationService(principalDetailService, jwtTokenValidator)

    given("authenticationService 테스트") {

        val user = UserFixture.createWithId(1)
        val token = "token"

        val claims = mockk<Claims>()
        val principalDetails = PrincipalDetails(user = user)

        `when`("토큰이 유효할 때") {

            every { claims[EMAIL_CLAIM] } returns user.email
            every { jwtTokenValidator.verifyToken(token) } returns claims
            every { principalDetailService.loadUserByUsername(user.email) } returns principalDetails

            then("Authentication 객체를 반환해야 한다") {
                val authentication: Authentication = authenticationService.getAuthentication(token)

                authentication.name shouldBe principalDetails.username
                authentication.credentials shouldBe principalDetails.password
            }
        }
    }
})

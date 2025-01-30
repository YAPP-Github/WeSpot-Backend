package com.wespot.auth.service


import com.wespot.auth.JwtTokenInfo
import com.wespot.auth.PrincipalDetails
import com.wespot.auth.port.`in`.AuthenticationUseCase
import com.wespot.auth.service.jwt.JwtTokenValidator
import io.jsonwebtoken.Claims
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper
import org.springframework.stereotype.Component

@Component
class AuthenticationService(
    private val principalDetailService: PrincipalDetailService,
    private val jwtTokenValidator: JwtTokenValidator,
) : AuthenticationUseCase {

    override fun getAuthentication(token: String): Authentication {
        val claims: Claims = jwtTokenValidator.verifyToken(token)
        val email: String = claims[JwtTokenInfo.EMAIL_CLAIM] as String
        val principalDetails = principalDetailService.loadUserByUsername(email) as PrincipalDetails

        return UsernamePasswordAuthenticationToken(
            principalDetails,
            principalDetails.password,
            NullAuthoritiesMapper().mapAuthorities(principalDetails.authorities)
        )
    }

}

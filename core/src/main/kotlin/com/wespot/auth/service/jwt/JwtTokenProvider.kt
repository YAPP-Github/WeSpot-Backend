package com.wespot.auth.service.jwt

import com.wespot.auth.JwtTokenInfo.ACCESS_TOKEN
import com.wespot.auth.JwtTokenInfo.EMAIL_CLAIM
import com.wespot.auth.JwtTokenInfo.REFRESH_TOKEN
import com.wespot.auth.dto.response.TokenResponse
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*


@Service
class JwtTokenProvider(
    @Value("\${jwt.secret}")
    private val secretKey: String,

    @Value("\${jwt.accessTokenExpireTime}")
    private val accessTokenExpireTime: Long,

    @Value("\${jwt.refreshTokenExpireTime}")
    private val refreshTokenExpireTime: Long,

) {
    private val key: Key = Keys.hmacShaKeyFor(secretKey.toByteArray())

    fun generateToken(authentication: Authentication): TokenResponse {

        val now = Date().time
        val accessTokenExpiresIn = Date(now + accessTokenExpireTime)

        val accessToken = Jwts.builder()
            .setSubject(ACCESS_TOKEN)
            .claim(EMAIL_CLAIM, authentication.name)
            .setExpiration(accessTokenExpiresIn)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

        val refreshToken = Jwts.builder()
            .setSubject(REFRESH_TOKEN)
            .claim(EMAIL_CLAIM, authentication.name)
            .setExpiration(Date(now + refreshTokenExpireTime))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

        return TokenResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )

    }

}


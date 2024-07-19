package com.wespot.auth.service.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Component
import java.security.Key

@Component
class JwtTokenValidator(
    @Value("\${jwt.secret}")
    private val secretKey: String,
){
    private val key: Key = Keys.hmacShaKeyFor(secretKey.toByteArray())

    fun verifyToken(token: String): Claims {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: Exception) {
            throw BadCredentialsException("accessToken의 정보가 올바르지 않습니다.", e)
        }
    }

}

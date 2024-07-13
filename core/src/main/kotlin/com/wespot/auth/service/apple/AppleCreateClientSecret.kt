package com.wespot.auth.service.apple

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo
import org.bouncycastle.openssl.PEMParser
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.StringReader
import java.security.PrivateKey
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
class AppleCreateClientSecret(
    @Value("\${apple.appleTeamId}")
    private val appleTeamId: String,

    @Value("\${apple.appleAud}")
    private val appleAud: String,

    @Value("\${apple.appleKeyId}")
    private val appleKeyId: String,

    @Value("\${apple.appleKey}")
    private val appleKey: String
) {
    fun createClientSecret(): String {
        val expirationDate = Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant())
        val jwtHeader: Map<String, Any?> = mapOf("kid" to appleKeyId, "alg" to "ES256")

        return Jwts.builder()
            .setHeader(jwtHeader)
            .setIssuer(appleTeamId)
            .setIssuedAt(Date(System.currentTimeMillis())) // 발행 시간
            .setExpiration(expirationDate) // 만료 시간
            .setAudience("https://appleid.apple.com")
            .setSubject(appleAud)
            .signWith(getPrivateKey(), SignatureAlgorithm.ES256)
            .compact()
    }

    private fun getPrivateKey(): PrivateKey {
        return try {
            val privateKeyContent = appleKey
            val pemReader = StringReader(privateKeyContent)
            val pemParser = PEMParser(pemReader)
            val converter = JcaPEMKeyConverter()
            val objects = pemParser.readObject() as PrivateKeyInfo
            converter.getPrivateKey(objects)
        } catch (e: Exception) {
            throw IllegalArgumentException ("Apple private key 생성 실패: ${e.message}")
        }
    }
}
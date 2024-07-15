package com.wespot.auth.service.apple

import com.wespot.auth.dto.apple.ApplePublicKeysResult
import org.springframework.stereotype.Component
import java.math.BigInteger
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.RSAPublicKeySpec
import java.util.*


@Component
class ApplePublicKeyGenerator {
    companion object {
        private const val SIGN_ALGORITHM_HEADER_KEY = "alg"
        private const val KEY_ID_HEADER_KEY = "kid"
        private const val POSITIVE_SIGN_NUMBER = 1
    }

    fun generatePublicKey(headers: Map<String, String>, applePublicKeys: ApplePublicKeysResult): PublicKey {
        val applePublicKey = applePublicKeys.getMatchesKey(
            headers[SIGN_ALGORITHM_HEADER_KEY],
            headers[KEY_ID_HEADER_KEY]
        )

        val nBytes = Base64.getUrlDecoder().decode(applePublicKey.n)
        val eBytes = Base64.getUrlDecoder().decode(applePublicKey.e)

        val n = BigInteger(POSITIVE_SIGN_NUMBER, nBytes)
        val e = BigInteger(POSITIVE_SIGN_NUMBER, eBytes)

        val publicKeySpec = RSAPublicKeySpec(n, e)

        return try {
            val keyFactory = KeyFactory.getInstance("RSA")
            keyFactory.generatePublic(publicKeySpec)
        } catch (exception: Exception) {
            throw IllegalArgumentException("Apple OAuth 로그인 중 public key 생성에 문제가 발생했습니다.", exception)
        }
    }
}

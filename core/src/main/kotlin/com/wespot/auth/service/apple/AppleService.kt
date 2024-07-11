package com.wespot.auth.service.apple

import com.wespot.auth.dto.AuthLoginRequest
import com.wespot.auth.dto.OAuthIdAndRefreshToken
import com.wespot.auth.dto.apple.AppleRevokeRequest
import com.wespot.auth.dto.apple.AppleTokenResult
import com.wespot.auth.service.SocialAuthService
import com.wespot.user.SocialType
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class AppleService(
    private val appleClient: AppleClient,
    private val applePublicKeyGenerator: ApplePublicKeyGenerator,
    private val appleJwtParser: AppleJwtParser,
    private val appleCreateClientSecret: AppleCreateClientSecret,
    @Value("\${apple.appleAud}") private val appleAud: String,
    @Value("\${apple.appleRedirectUri}") private val appleRedirectUri: String,
) : SocialAuthService {

    companion object {
        private const val NOT_SUPPORTED = "not supported"
        private const val GRANT_TYPE = "authorization_code"
        private const val TOKEN_TYPE_HINT = "refresh_token"
    }

    override fun fetchAuthToken(authLoginRequest: AuthLoginRequest): OAuthIdAndRefreshToken {
        val appleId = getAppleId(authLoginRequest.identityToken
            ?: throw IllegalArgumentException("Apple ID token is null"))
        val appleTokenResult = generateAuthToken(authLoginRequest.authorizationCode
            ?: throw IllegalArgumentException("Authorization code is null"))

        return OAuthIdAndRefreshToken(
            oAuthId = appleId,
            refreshToken = appleTokenResult.refreshToken ?: NOT_SUPPORTED
        )
    }

    override fun isSupport(socialType: SocialType): Boolean {
        return socialType == SocialType.APPLE
    }

    private fun getAppleId(identityToken: String): String {
        val headers = appleJwtParser.parseHeaders(identityToken)
        val applePublicKeys = appleClient.getApplePublicKeys()
        val publicKey = applePublicKeyGenerator.generatePublicKey(headers, applePublicKeys)
        val claims: Claims = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(identityToken).body

        return claims.subject
    }

    private fun generateAuthToken(authorizationCode: String): AppleTokenResult {
        val clientSecret = appleCreateClientSecret.createClientSecret()

        return appleClient.getToken(
            clientId = appleAud,
            clientSecret = clientSecret,
            code = authorizationCode,
            grantType = GRANT_TYPE,
            redirectUri = appleRedirectUri
        )
    }

    override fun revoke(socialId: String, socialRefreshToken: String?): Boolean {
        val response = appleClient.revoke(
            AppleRevokeRequest(
                clientId = appleAud,
                clientSecret = appleCreateClientSecret.createClientSecret(),
                token = socialRefreshToken ?: throw IllegalArgumentException("Refresh token is null"),
                tokenTypeHint = TOKEN_TYPE_HINT
            )
        )

        require(response.status() == 200) { "Failed to revoke the token. Status: ${response.status()}" }
        return true
    }
}

package com.wespot.auth.service.apple

import com.wespot.auth.dto.apple.ApplePublicKeysResult
import com.wespot.auth.dto.apple.AppleRevokeRequest
import com.wespot.auth.dto.apple.AppleTokenResult
import com.wespot.auth.service.SocialHeaderConfiguration
import feign.Response
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "apple",
    url = "https://appleid.apple.com/auth",
    configuration = [SocialHeaderConfiguration::class]
)
interface AppleClient {

    /**
     * Apple 공개키 가져오기
     * https://appleid.apple.com/auth/keys
     */
    @GetMapping("/keys")
    fun getApplePublicKeys(): ApplePublicKeysResult

    /**
     * Apple get Token
     * https://developer.apple.com/documentation/sign_in_with_apple/generate_and_validate_tokens
     */
    @PostMapping("/token", produces = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun getToken(
        @RequestParam("client_id") clientId: String,
        @RequestParam("client_secret") clientSecret: String,
        @RequestParam("code") code: String,
        @RequestParam("grant_type") grantType: String,
        @RequestParam("redirect_uri") redirectUri: String,
    ): AppleTokenResult

    /**
     * Apple revoke Token
     * https://developer.apple.com/documentation/sign_in_with_apple/revoking_tokens
     */
    @PostMapping("/revoke", produces = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun revoke(
        @RequestBody appleRevokeRequest: AppleRevokeRequest
    ): Response
}

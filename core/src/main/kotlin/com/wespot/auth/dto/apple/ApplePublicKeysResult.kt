package com.wespot.auth.dto.apple

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import org.springframework.http.HttpStatus

/**
 * https://developer.apple.com/documentation/sign_in_with_apple/fetch_apple_s_public_key_for_verifying_token_signature 참고
 */
data class ApplePublicKeysResult(
    // 공개키 목록
    val keys: List<ApplePublicKey>
) {
    fun getMatchesKey(alg: String?, kid: String?): ApplePublicKey {
        return keys
            .firstOrNull { key -> key.alg == alg && key.kid == kid }
            ?: throw CustomException(
                HttpStatus.BAD_REQUEST,
                ExceptionView.TOAST,
                "Apple JWT 값의 alg, kid 정보가 올바르지 않습니다."
            )
    }
}

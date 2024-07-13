package com.wespot.auth.service.kakao

import com.wespot.auth.dto.AuthLoginRequest
import com.wespot.auth.dto.OAuthIdAndRefreshToken
import com.wespot.auth.service.SocialAuthService
import com.wespot.user.SocialType
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class KakaoService(
    private val kakaoClient: KakaoClient,
    @Value("\${kakao.adminKey}") private val adminKey: String
) : SocialAuthService {

    companion object {
        private const val NOT_SUPPORTED = "not supported"
        private const val KAKAO_PREFIX = "KakaoAK "
    }

    override fun fetchAuthToken(authLoginRequest: AuthLoginRequest): OAuthIdAndRefreshToken {
        val kakaoId = getKakaoId(authLoginRequest.identityToken
            ?: throw IllegalArgumentException("Kakao ID가 입력되지 않았습니다."))

        return OAuthIdAndRefreshToken(
            oAuthId = kakaoId, refreshToken = NOT_SUPPORTED
        )
    }

    override fun isSupport(socialType: SocialType): Boolean {
        return socialType == SocialType.KAKAO
    }

    override fun revoke(socialId: String, socialRefreshToken: String?): Boolean {
        kakaoClient.unlink(
            adminKey = "$KAKAO_PREFIX$adminKey",
            targetIdType = "user_id",
            targetId = socialId
        )
        return true
    }

    private fun getKakaoId(accessToken: String): String {
        val kakaoUserInfo = kakaoClient.getUserInfo("Bearer $accessToken")
        require(kakaoUserInfo.id > 0) { "Kakao 로그인에 실패하였습니다. 사용자 정보를 가져오는 데 문제가 발생하였습니다." }
        return kakaoUserInfo.id.toString()
    }
}

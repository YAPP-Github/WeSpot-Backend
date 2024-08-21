package com.wespot.auth.service.kakao

import com.wespot.auth.dto.request.AuthLoginRequest
import com.wespot.auth.dto.response.SocialResponse
import com.wespot.auth.service.SocialAuthService
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.user.SocialType
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
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

    override fun fetchAuthToken(authLoginRequest: AuthLoginRequest): SocialResponse {
        val kakaoId = getKakaoId(
            authLoginRequest.identityToken
                ?: throw CustomException(HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "Kakao ID가 입력되지 않았습니다.")
        )

        return SocialResponse(
            socialId = kakaoId,
            socialRefreshToken = NOT_SUPPORTED,
            socialEmail = NOT_SUPPORTED
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
        require(kakaoUserInfo.id > 0) {
            CustomException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ExceptionView.TOAST,
                "Kakao 로그인에 실패하였습니다. 사용자 정보를 가져오는 데 문제가 발생하였습니다."
            )
        }
        return kakaoUserInfo.id.toString()
    }
}

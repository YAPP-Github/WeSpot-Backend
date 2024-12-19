package com.wespot.auth.service.kakao

import com.wespot.auth.KakaoTemplateType
import com.wespot.auth.dto.response.KakaoTemplateResponse
import com.wespot.auth.port.`in`.KakaoTemplateUseCase
import com.wespot.auth.port.out.KakaoTemplatePort
import com.wespot.auth.service.SecurityUtils
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.user.port.out.UserPort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class KakaoTemplateService(
    private val kakaoTemplatePort: KakaoTemplatePort,
    private val userPort: UserPort
) : KakaoTemplateUseCase {

    override fun getKakaoTemplate(type: KakaoTemplateType): KakaoTemplateResponse {
        val kakaoTemplate = kakaoTemplatePort.getKakaoTemplate(type = type)
            ?: throw CustomException(
                status = HttpStatus.NOT_FOUND,
                view = ExceptionView.TOAST,
                "해당 타입에는 카카오 템플릿이 존재하지 않습니다."
            )
        if (kakaoTemplate.type == KakaoTemplateType.INVITE) {
            return KakaoTemplateResponse.ofWithUser(kakaoTemplate, SecurityUtils.getLoginUser(userPort))
        }
        return KakaoTemplateResponse.from(kakaoTemplate)
    }

}

package com.wespot.config.security

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpStatus
import org.springframework.web.servlet.HandlerInterceptor

class AdminInterceptor(
    private val password: String
) : HandlerInterceptor {

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        val header = request.getHeader(AUTHORIZATION)
            ?: throw CustomException(
                HttpStatus.FORBIDDEN,
                ExceptionView.TOAST,
                "어드민 기능은 특정 Password로 인증을 진행해야합니다"
            )

        if (password != header) {
            throw CustomException(HttpStatus.FORBIDDEN, ExceptionView.TOAST, "Password가 틀렸습니다")
        }

        return true
    }

}

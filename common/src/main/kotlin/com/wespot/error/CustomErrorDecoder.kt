package com.wespot.error

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.http.HttpStatus

class CustomErrorDecoder : ErrorDecoder {
    override fun decode(methodKey: String, response: Response): Exception {
        return when (response.status()) {
            400 -> CustomException(
                HttpStatus.BAD_REQUEST,
                ExceptionView.TOAST,
                "OAuth 요청이 잘못되었습니다 (Bad request)"
            )

            401 -> CustomException(
                HttpStatus.UNAUTHORIZED,
                ExceptionView.TOAST,
                "OAuth 인증에 실패하였습니다 (Authentication failed)"
            )

            404 -> CustomException(
                HttpStatus.NOT_FOUND,
                ExceptionView.TOAST,
                "OAuth 리소스를 찾을 수 없습니다 (Resource not found)"
            )

            500 -> CustomException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ExceptionView.TOAST,
                "OAuth 내부 서버 오류입니다 (Internal server error)"
            )

            else -> CustomException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ExceptionView.TOAST,
                "OAuth 연결 중 알 수 없는 오류가 발생했습니다)"
            )
        }
    }
}

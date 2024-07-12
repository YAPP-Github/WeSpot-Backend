package com.wespot.error

import feign.Response
import feign.codec.ErrorDecoder
import java.util.NoSuchElementException

class CustomErrorDecoder : ErrorDecoder {
    override fun decode(methodKey: String, response: Response): Exception {
        return when (response.status()) {
            400 -> IllegalArgumentException("OAuth 요청이 잘못되었습니다 (Bad request)")
            401 -> IllegalArgumentException("OAuth 인증에 실패하였습니다 (Authentication failed)")
            404 -> NoSuchElementException("OAuth 리소스를 찾을 수 없습니다 (Resource not found)")
            500 -> RuntimeException("OAuth 내부 서버 오류입니다 (Internal server error)")
            else -> RuntimeException("OAuth 연결 중 알 수 없는 오류가 발생했습니다)")
        }
    }
}

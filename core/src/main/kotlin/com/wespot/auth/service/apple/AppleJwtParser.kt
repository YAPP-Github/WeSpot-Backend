package com.wespot.auth.service.apple

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2StreamChannelBootstrap
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.util.*

@Component
class AppleJwtParser(private val objectMapper: ObjectMapper) {

    companion object {
        private const val HEADER_INDEX = 0
    }

    fun parseHeaders(identityToken: String): Map<String, String> {
        return try {
            val encodedHeader = identityToken.split(".")[HEADER_INDEX]
            val decodedHeader = String(Base64.getUrlDecoder().decode(encodedHeader))
            objectMapper.readValue(decodedHeader, object : TypeReference<Map<String, String>>() {})
        } catch (e: Exception) {
            throw CustomException(
                HttpStatus.BAD_REQUEST,
                ExceptionView.TOAST,
                "Apple OAuth Identity Token 형식이 올바르지 않습니다."
            )
        }
    }
}

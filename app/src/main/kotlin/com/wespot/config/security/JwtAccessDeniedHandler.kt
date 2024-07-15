package com.wespot.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ProblemDetail
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import java.net.URI
import kotlin.text.Charsets.UTF_8

@Component
class JwtAccessDeniedHandler(
    private val objectMapper: ObjectMapper
) : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AccessDeniedException
    ) {

        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = UTF_8.name()
        response.status = HttpStatus.FORBIDDEN.value()

        val body = objectMapper.writeValueAsString(
            ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED,
                AccessDeniedException("자신의 것만 가능합니다").message!!,
            ).apply {
                type = URI.create("/errors/forbidden")
                instance = URI.create(request.requestURI)
            }
        )
        response.writer.write(body)

    }

}
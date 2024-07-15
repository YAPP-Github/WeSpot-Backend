package com.wespot.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ProblemDetail
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.net.URI
import kotlin.text.Charsets.UTF_8

@Component
class JwtAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper
) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException?
    ) {

        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = UTF_8.name()
        response.status = HttpStatus.UNAUTHORIZED.value()

        val body = objectMapper.writeValueAsString(
            ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED,
                BadCredentialsException("").message!!,
            ).apply {
                type = URI.create("/errors/unauthenticated")
                instance = URI.create(request.requestURI)
            }
        )
        response.writer.write(body)

    }
}


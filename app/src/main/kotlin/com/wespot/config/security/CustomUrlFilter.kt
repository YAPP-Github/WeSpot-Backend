package com.wespot.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ProblemDetail
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter
import java.net.URI
import kotlin.text.Charsets.UTF_8

@Component
class CustomUrlFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    private val antPathMatcher = AntPathMatcher()

    private val validUrlPatterns = listOf(
        "/health",
        "/",
        "/h2-console",
        "/api/v1/auth/reissue",
        "/api/v1/auth/login",
        "/api/v1/auth/signup",
        "/api/v1/auth/revoke",
        "/api/v1/users/me",
        "/api/v1/users/backgrounds",
        "/api/v1/users/characters",
        "/api/v1/users/search",
        "/api/v1/votes/options",
        "/api/v1/votes",
        "/api/v1/votes/tops",
        "/api/v1/votes/sent",
        "/api/v1/votes/received",
        "/api/v1/votes/sent/options",
        "/api/v1/votes/received/options",
        "/api/v1/reports/users"
    )

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        if (!isValidUrl(request)) {
            handleInvalidUrl(request, response)
            return
        }
        filterChain.doFilter(request, response)

    }

    private fun isValidUrl(request: HttpServletRequest): Boolean {

        val requestUri = request.requestURI

        return validUrlPatterns.any { antPathMatcher.match(it, requestUri) }

    }

    private fun handleInvalidUrl(
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {

        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = UTF_8.name()
        response.status = HttpStatus.NOT_FOUND.value()

        val body = objectMapper.writeValueAsString(
            ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                NoSuchFieldException("잘못된 URL입니다.").message!!,
            ).apply {
                type = URI.create("/errors/not-found")
                instance = URI.create(request.requestURI)
            }
        )

        response.writer.write(body)

    }
}

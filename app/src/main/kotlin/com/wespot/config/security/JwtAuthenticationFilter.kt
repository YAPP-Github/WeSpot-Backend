package com.wespot.config.security

import com.wespot.auth.JwtTokenInfo
import com.wespot.auth.port.`in`.AuthenticationUseCase
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val authenticationUseCase: AuthenticationUseCase,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = extractToken(request)
        if (token != null && StringUtils.hasText(token)) {
            try {
                authenticateUserByToken(token)
            } catch (e: BadCredentialsException) {
                jwtAuthenticationEntryPoint.commence(request, response, authException = null)
                return
            }
        }
        filterChain.doFilter(request, response)
    }

    private fun extractToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(JwtTokenInfo.AUTHORIZATION_HEADER)
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtTokenInfo.BEARER_TYPE)) {
            bearerToken.substring(JwtTokenInfo.BEARER_TYPE.length).trim()
        } else null
    }

    private fun authenticateUserByToken(token: String) {
        try {
            val authentication = authenticationUseCase.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        } catch (e: BadCredentialsException) {
            SecurityContextHolder.clearContext()
        }
    }
}

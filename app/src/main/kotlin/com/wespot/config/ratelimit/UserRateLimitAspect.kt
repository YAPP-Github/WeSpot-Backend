package com.wespot.config.ratelimit

import com.wespot.auth.PrincipalDetails
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Aspect
@Component
class UserRateLimitAspect(
    private val userRateLimiter: UserRateLimiter,
) {

    @Around("@annotation(com.wespot.config.ratelimit.UserRateLimit)")
    fun around(joinPoint: ProceedingJoinPoint): Any? {
        val endpoint = joinPoint.signature.name
        val userKey = resolveUserKey()

        if (!userRateLimiter.tryConsume(userKey, endpoint)) {
            throw CustomException(
                status = HttpStatus.TOO_MANY_REQUESTS,
                view = ExceptionView.TOAST,
                message = "요청이 너무 빠릅니다. 잠시 후 다시 시도해주세요.",
            )
        }

        return joinPoint.proceed()
    }

    private fun resolveUserKey(): String {
        val principal = SecurityContextHolder.getContext().authentication?.principal
        if (principal is PrincipalDetails) {
            return "user:${principal.username}"
        }

        val request = (RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes)?.request
        val ip = request?.getHeader("X-Forwarded-For")?.split(",")?.firstOrNull()?.trim()
            ?: request?.remoteAddr
            ?: "unknown"
        return "ip:$ip"
    }
}

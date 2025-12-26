package com.wespot.logging

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component


@Aspect
@Component
class ApiLogging(
) {

    companion object {
        private val log = org.slf4j.LoggerFactory.getLogger(ApiLogging::class.java)
    }

    @Pointcut(
        """
            within(@org.springframework.web.bind.annotation.RestController *)
            ||
            within(@org.springframework.stereotype.Controller *)
            """
    )
    fun restControllerMethods() {
    }

    @Around("restControllerMethods()")
    @Throws(Throwable::class)
    fun measureLatency(joinPoint: ProceedingJoinPoint): Any {
        val start = System.currentTimeMillis()

        val result = joinPoint.proceed()

        val end = System.currentTimeMillis()
        val latency = end - start

        val methodName = joinPoint.signature.toShortString()
        log.info("[API LATENCY] {} took {} ms", methodName, latency)

        return result
    }

}

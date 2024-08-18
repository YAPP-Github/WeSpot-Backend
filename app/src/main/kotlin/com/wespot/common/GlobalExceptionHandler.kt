package com.wespot.common

import com.wespot.common.`in`.ErrorNotificationUseCase
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.net.URI
import java.time.LocalDateTime

@ControllerAdvice
class GlobalExceptionHandler(
    private val errorNotificationUseCase: ErrorNotificationUseCase,
    private val environment: Environment
) {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
        exception: IllegalArgumentException,
        request: HttpServletRequest
    ): ResponseEntity<ProblemDetail> {
        notifyException(false, request, exception)
        logger.error("요청된 정보가 잘못되었습니다.", exception)

        val problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            exception.message
        ).apply {
            type = URI.create("/errors/illegal-argument")
            instance = URI.create(request.requestURI)
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(problemDetail)
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(
        exception: IllegalStateException,
        request: HttpServletRequest
    ): ResponseEntity<ProblemDetail> {
        notifyException(false, request, exception)
        logger.error("잘못된 상태입니다", exception)

        val problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.CONFLICT,
            exception.message
        ).apply {
            type = URI.create("/errors/conflict")
            instance = URI.create(request.requestURI)
        }

        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(problemDetail)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupported(
        exception: HttpRequestMethodNotSupportedException,
        request: HttpServletRequest
    ): ResponseEntity<ProblemDetail> {
        notifyException(false, request, exception)
        logger.error("지원하지 않는 HTTP 메소드입니다.", exception)

        val problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            exception.message
        ).apply {
            type = URI.create("/errors/method-not-supported")
            instance = URI.create(request.requestURI)
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(problemDetail)
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElementException(
        exception: NoSuchElementException,
        request: HttpServletRequest
    ): ResponseEntity<ProblemDetail> {
        notifyException(false, request, exception)
        logger.error("자원을 찾을 수 없습니다.", exception)

        val problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.NOT_FOUND,
            exception.message
        ).apply {
            type = URI.create("/errors/no-such-element")
            instance = URI.create(request.requestURI)
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(problemDetail)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(
        exception: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ProblemDetail> {
        notifyException(true, request, exception)
        logger.error("서버에서 알 수 없는 에러가 발생했습니다.", exception)

        val problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "서버에서 알 수 없는 에러가 발생했습니다."
        ).apply {
            type = URI.create("/errors/internal-server-error")
            instance = URI.create(request.requestURI)
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(problemDetail)
    }

    private fun notifyException(isError: Boolean, request: HttpServletRequest, exception: Exception) {
        if (environment.activeProfiles.contains("local")) {
            return
        }

        val headers = request.headerNames.toList().joinToString("\n") { headerName ->
            "$headerName: ${request.getHeader(headerName)}"
        }

        val parameters = request.parameterNames.toList().joinToString("\n") { paramName ->
            "$paramName: ${request.getParameter(paramName)}"
        }

        val fullStackTrace = exception.stackTraceToString().take(3000)

        // 예외 발생 알림을 디스코드에 전송
        errorNotificationUseCase.notifyError(
            isError,
            "### 🕖 발생 시간\n" +
                "${LocalDateTime.now()}\n" +
                "### 📎 요청 URI\n" +
                "${request.requestURI} (${request.method})\n" +
                "### 🛠 요청자 정보\n" +
                "- IP: ${request.remoteAddr}\n" +
                "- 사용자: ${request.remoteUser ?: "Unknown"}\n" +
                "- 헤더:\n" +
                "```\n" +
                "$headers\n" +
                "```\n" +
                "- 요청 파라미터:\n" +
                "```\n" +
                "$parameters\n" +
                "```\n" +
                "### ✅ 예외 정보\n" +
                "- 예외 클래스: ${exception.javaClass.canonicalName}\n" +
                "- 예외 메시지: ${exception.message ?: "No message"}\n" +
                "- 발생 위치: ${extractExceptionSource(exception)}\n"
        )

        errorNotificationUseCase.notifyError(
            isError,
            """
            ### 🗂 스택 트레이스 (부분)
            ```            $fullStackTrace
            ```        """.trimIndent()
        )
    }


    private fun extractExceptionSource(exception: Exception): String {
        val stackTrace = exception.stackTrace
        if (stackTrace.isNotEmpty()) {
            return stackTrace[0].toString()
        }
        return "Unknown Source"
    }

}

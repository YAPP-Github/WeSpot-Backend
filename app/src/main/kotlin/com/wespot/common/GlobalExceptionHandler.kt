package com.wespot.common

import com.wespot.ReasonPhraseUtil
import com.wespot.common.`in`.ErrorNotificationUseCase
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionResponse
import com.wespot.exception.ExceptionView
import feign.FeignException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.env.Environment
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.net.URI
import java.time.LocalDateTime

@ControllerAdvice
class GlobalExceptionHandler(
    private val errorNotificationUseCase: ErrorNotificationUseCase,
    private val environment: Environment
) : ResponseEntityExceptionHandler() {

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(
        exception: CustomException,
        request: HttpServletRequest
    ): ResponseEntity<ExceptionResponse> {
        notifyException(false, request, exception)
        logger.warn("예외가 발생했습니다.", exception)

        val problemDetail = ProblemDetail.forStatusAndDetail(
            exception.status,
            exception.message
        ).apply {
            type = ReasonPhraseUtil.createErrorTypeInProblemDetail(exception.status)
            instance = URI.create(request.requestURI)
        }

        return ResponseEntity.status(exception.status)
            .body(ExceptionResponse(exception.view, problemDetail))
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
            type = ReasonPhraseUtil.createErrorTypeInProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR)
            instance = URI.create(request.requestURI)
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(problemDetail)
    }

    @ExceptionHandler(FeignException::class)
    fun handleFeignException(
        exception: FeignException,
        request: HttpServletRequest
    ): ResponseEntity<ExceptionResponse> {
        notifyException(false, request, exception)
        logger.warn("외부 API 호출 중 예외가 발생했습니다.", exception)

        val problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            exception.message
        ).apply {
            type = ReasonPhraseUtil.createErrorTypeInProblemDetail(HttpStatus.BAD_REQUEST)
            instance = URI.create(request.requestURI)
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ExceptionResponse(ExceptionView.TOAST, problemDetail))
    }

    override fun handleMethodArgumentNotValid(
        exception: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.warn("잘못된 요청입니다.", exception)

        val problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            exception.message
        ).apply {
            type = ReasonPhraseUtil.createErrorTypeInProblemDetail(HttpStatus.BAD_REQUEST)
            instance = URI.create(request.getDescription(false))
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ExceptionResponse(ExceptionView.TOAST, problemDetail))
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

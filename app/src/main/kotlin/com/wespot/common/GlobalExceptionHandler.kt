package com.wespot.common

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.net.URI

@ControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
        exception: IllegalArgumentException,
        request: HttpServletRequest
    ): ResponseEntity<ProblemDetail> {
        logger.error("요청된 정보가 잘못되었습니다.", exception)

        val problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            exception.message
        ).apply {
            type = URI.create("/errors/illegal-argument")
            instance = URI.create(request.requestURI)
        }
        return ResponseEntity(problemDetail, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElementException(
        exception: NoSuchElementException,
        request: HttpServletRequest
    ): ResponseEntity<ProblemDetail> {
        logger.error("자원을 찾을 수 없습니다.", exception)

        val problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.NOT_FOUND,
            exception.message
        ).apply {
            type = URI.create("/errors/no-such-element")
            instance = URI.create(request.requestURI)
        }
        return ResponseEntity(problemDetail, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(
        exception: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ProblemDetail> {
        logger.error("서버에서 알 수 없는 에러가 발생했습니다.", exception)

        val problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "서버에서 알 수 없는 에러가 발생했습니다."
        ).apply {
            type = URI.create("/errors/internal-server-error")
            instance = URI.create(request.requestURI)
        }
        return ResponseEntity(problemDetail, HttpStatus.NOT_FOUND)
    }

}

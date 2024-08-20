package com.wespot.exception

import org.springframework.http.ProblemDetail

class ExceptionResponse(
    val view: ExceptionView,
    val problemDetail: ProblemDetail
) {
}

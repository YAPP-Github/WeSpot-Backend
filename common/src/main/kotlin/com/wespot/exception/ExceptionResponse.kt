package com.wespot.exception

import org.springframework.http.ProblemDetail

class ExceptionResponse(
    val view: ExceptionView,
    val type: String,
    val title: String,
    val status: Int,
    val detail: String,
    val instance: String,
) {
    companion object {

        fun of(view: ExceptionView, problemDetail: ProblemDetail): ExceptionResponse {
            return ExceptionResponse(
                view = view,
                type = problemDetail.type.path,
                title = problemDetail.title!!,
                status = problemDetail.status,
                detail = problemDetail.detail!!,
                instance = problemDetail.instance!!.path,
            )
        }

    }
}

package com.wespot.exception

import org.springframework.http.HttpStatus

class CustomException(
    val status: HttpStatus,
    val view: ExceptionView,
    override val message: String,
) : RuntimeException() {

}

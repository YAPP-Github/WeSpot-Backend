package com.wespot.exception

import org.springframework.http.HttpStatus

class CustomException(
    val status: HttpStatus,
    override val message: String,
    val view: ExceptionView
) : RuntimeException() {

}

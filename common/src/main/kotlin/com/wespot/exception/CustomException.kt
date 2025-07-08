package com.wespot.exception

import org.springframework.http.HttpStatus

class CustomException(
    val status: HttpStatus = HttpStatus.BAD_REQUEST,
    val view: ExceptionView = ExceptionView.TOAST,
    override val message: String = "",
) : RuntimeException() {

}

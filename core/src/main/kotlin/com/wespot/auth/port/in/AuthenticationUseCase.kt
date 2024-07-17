package com.wespot.auth.port.`in`

import org.springframework.security.core.Authentication

fun interface AuthenticationUseCase {

    fun getAuthentication(token: String): Authentication

}

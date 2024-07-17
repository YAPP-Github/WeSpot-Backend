package com.wespot.auth.port.out

import com.wespot.auth.dto.AuthData

interface AuthDataPort {

    fun saveAuthData(token: String, authData: AuthData)

    fun getAuthData(token: String): AuthData?

}
package com.wespot.auth.port.out

import com.wespot.auth.RefreshToken
import com.wespot.user.User

interface RefreshTokenPort {

    fun save(refreshToken: RefreshToken): RefreshToken

    fun findByUserId(userId: Long): RefreshToken?

    fun saveOrUpdate(refreshToken: RefreshToken): RefreshToken

    fun deleteByUserId(userId: Long)

    fun findByRefreshToken(refreshToken: String): RefreshToken?

}
package com.wespot.auth

import com.wespot.user.User
import java.time.LocalDateTime

data class PersonalInfo(
    val id: Long,
    val email: String,
    val name: String,
    val socialId: String,
    val socialEmail: String?,
    val socialRefreshToken: String?,
    val restriction: Long,
    val storedAt: LocalDateTime
){
    companion object{

        fun create(
            user: User
        ) = PersonalInfo(
            id = 0,
            email = user.email,
            name = user.name,
            socialId = user.social.socialId,
            socialEmail = user.social.socialEmail,
            socialRefreshToken = user.social.socialRefreshToken,
            restriction = user.restriction.id,
            storedAt = LocalDateTime.now()
        )
    }
}

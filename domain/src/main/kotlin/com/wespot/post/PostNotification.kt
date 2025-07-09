package com.wespot.post

import com.wespot.user.User
import java.time.LocalDateTime

class PostNotification(
    val id: Long = 0L,
    val user: User,
    val postId: Long,
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {

    companion object {

        fun of(user: User, postId: Long): PostNotification {
            return PostNotification(
                user = user,
                postId = postId
            )
        }

    }

}
